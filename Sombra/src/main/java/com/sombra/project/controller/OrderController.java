package com.sombra.project.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sombra.project.dto.CommodityDto;
import com.sombra.project.dto.OrderDto;
import com.sombra.project.dto.UserDto;
import com.sombra.project.service.CommodityService;
import com.sombra.project.service.DocumentService;
import com.sombra.project.service.MailService;
import com.sombra.project.service.OrderService;
import com.sombra.project.service.UserService;
import com.sombra.project.validation.ValidationException;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private UserService userService;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private MailService mailService;

	@RequestMapping(value = "/admin/orders", method = RequestMethod.GET)
	public String adminOrders(Model model) {
		model.addAttribute("orders", orderService.findAllWithCommodityAndUser());
		model.addAttribute("commodities", commodityService.findAll());
		model.addAttribute("users", userService.findAll());
		return "admin-orders";
	}

	@RequestMapping(value = "/admin/orders/add", method = RequestMethod.POST)
	public @ResponseBody List<OrderDto> adminOrdersAdd(@RequestBody OrderDto orderDto) {
		orderDto.setCommodity(commodityService.findOne(orderDto.getCommodity().getId()));
		orderDto.setUser(userService.findOne(orderDto.getUser().getId()));
		orderService.save(orderDto);
		return orderService.findAll();
	}

	@RequestMapping(value = "/admin/orders/edit/{id}", method = RequestMethod.PUT)
	public @ResponseBody List<OrderDto> adminOrdersEdit(@PathVariable int id, @RequestBody OrderDto orderDto) {
		orderDto.setId(id);
		orderDto.setCommodity(commodityService.findOne(orderDto.getCommodity().getId()));
		orderDto.setUser(userService.findOne(orderDto.getUser().getId()));
		orderService.update(orderDto);
		return orderService.findAll();
	}

	@RequestMapping(value = "/admin/orders/delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody List<OrderDto> adminOrdersDelete(@PathVariable int id) {
		orderService.delete(id);
		return orderService.findAll();
	}

	@RequestMapping(value = "/cart/order/{id}", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> cartOrder(@PathVariable int id,
			@CookieValue(value = "cart", required = false, defaultValue = "") String cart,
			Authentication authentication, Model model, HttpServletResponse response) {
		orderService.makeOrder(id, ((UserDto) authentication.getPrincipal()).getId(),
				commodityService.getCartQuantity(id, cart));
		Cookie cookie = commodityService.deleteFromCart(id, cart);
		response.addCookie(cookie);
		Map<CommodityDto, Integer> commodities = commodityService.getCartCommodities(cookie.getValue());
		return commodityService.cartCommoditiesToJson(commodities);
	}

	@RequestMapping(value = "/user/orders", method = RequestMethod.GET)
	public String userOrders(Authentication authentication, Model model) {
		model.addAttribute("orders", orderService.findOrdersByUser(((UserDto) authentication.getPrincipal()).getId()));
		return "user-orders";
	}

	@RequestMapping(value = "/user/orders/pay", method = RequestMethod.POST)
	public String userOrders(@RequestParam int id, @RequestParam String card, Authentication authentication,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		try {
			String document = UUID.randomUUID().toString();
			UserDto userDto = ((UserDto) authentication.getPrincipal());
			orderService.confirmPayment(id, userDto.getId(), card, document);
			String fontUrl = "";
			fontUrl = request.getServletContext().getRealPath("/fonts/times.ttf");
			OrderDto orderDto = orderService.findOne(id);
			documentService.createDocument(orderDto, fontUrl);
			String subject = "Payment";
			String text = "Dear <b>" + userDto.getName() + "</b>, \nThank you for your payment";
			String file = System.getProperty("catalina.home") + "/resources/orders/" + orderDto.getId() + "/"
					+ orderDto.getDocument() + ".pdf";
			mailService.sendFile(subject, text, userDto.getEmail(), file);
			redirectAttributes.addFlashAttribute("success",
					"Payment successful. A payment check has been sent to your e-mail");
		} catch (ValidationException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/user/orders";
	}

}
