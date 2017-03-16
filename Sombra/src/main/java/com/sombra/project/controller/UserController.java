package com.sombra.project.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sombra.project.dto.UserDto;
import com.sombra.project.service.MailService;
import com.sombra.project.service.UserService;
import com.sombra.project.validation.ValidationException;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private MailService mailService;
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin() {
		return "admin";
	}
	
	@RequestMapping(value = "/admin/users", method = RequestMethod.GET)
	public String adminUsers(Model model, Authentication authentication) {
		model.addAttribute("users", userService.findAll());
		return "admin-users";
	}

	@RequestMapping(value = "/admin/users/add", method = RequestMethod.POST)
	public @ResponseBody List<UserDto> adminUsersAdd(@RequestBody UserDto userDto) {
		userService.save(userDto);
		return userService.findAll();
	}

	@RequestMapping(value = "/admin/users/edit/{id}", method = RequestMethod.PUT)
	public @ResponseBody List<UserDto> adminUsersEdit(@PathVariable int id, @RequestBody UserDto userDto) {
		userDto.setId(id);
		userService.update(userDto);
		return userService.findAll();
	}

	@RequestMapping(value = "/admin/users/delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody List<UserDto> adminUsersDelete(@PathVariable int id) {
		userService.delete(id);
		return userService.findAll();
	}

	@RequestMapping(value = "/admin/users/editImage/{id}", method = RequestMethod.POST)
	public @ResponseBody List<UserDto> adminUsersEditImage(@PathVariable int id, @RequestParam MultipartFile image) {
		userService.editImage(id, image);
		return userService.findAll();
	}

	@RequestMapping(value = "/admin/users/deleteImage/{id}", method = RequestMethod.POST)
	public @ResponseBody List<UserDto> adminUsersDeleteImage(@PathVariable int id) {
		userService.deleteImage(id);
		return userService.findAll();
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		model.addAttribute("user", new UserDto());
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute UserDto userDto, RedirectAttributes redirectAttributes) {
		String uuid = UUID.randomUUID().toString();
		userDto.setUuid(uuid);
		try {
			userService.register(userDto);
		} catch (ValidationException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/register";
		}
		String subject = "Registration";
		String text = "Dear <b>" + userDto.getName() + "</b>, \nPlease confirm your registration\n"
				+ "<a href=\"http://localhost:8080/confirm/" + uuid + "\">Confirmation Link</a>";
		mailService.sendMail(subject, text, userDto.getEmail());
		redirectAttributes.addFlashAttribute("success",
				"Dear <b>" + userDto.getName() + "</b>, please check E-Mail to activate your account");
		return "redirect:/login";
	}

	@RequestMapping(value = "/confirm/{uuid}", method = RequestMethod.GET)
	public String confirm(@PathVariable String uuid, RedirectAttributes redirectAttributes) {
		try {
			userService.confirm(uuid);
			redirectAttributes.addFlashAttribute("success",
					"Dear <b>" + userService.findByUuid(uuid).getName() + "</b>, your account has been activated");
			return "redirect:/login";
		} catch (NullPointerException e) {
			redirectAttributes.addFlashAttribute("error", "Account not found");
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String user() {
		return "redirect:/";
	}


	@RequestMapping(value = "/user/profile", method = RequestMethod.GET)
	public String userProfile(Model model, Authentication authentication) {
		model.addAttribute("user", userService.findOne(((UserDto) authentication.getPrincipal()).getId()));
		return "user-profile";
	} 
	
	@RequestMapping(value = "/user/profile/changeName", method = RequestMethod.POST)
	public String userProfileChangeName(@RequestParam String name, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		try {
			userService.changeName(((UserDto) authentication.getPrincipal()).getId(), name);
		} catch (ValidationException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/user/profile";
		}
		redirectAttributes.addFlashAttribute("success", "Name changed successfully");
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/user/profile/changeSurname", method = RequestMethod.POST)
	public String userProfileChangeSurname(@RequestParam String surname, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		try {
			userService.changeSurname(((UserDto) authentication.getPrincipal()).getId(), surname);
		} catch (ValidationException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/user/profile";
		}
		redirectAttributes.addFlashAttribute("success", "Surname changed successfully");
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/user/profile/changeEmail", method = RequestMethod.POST)
	public String userProfileChangeEmail(@RequestParam String email, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		try {
			userService.changeEmail(((UserDto) authentication.getPrincipal()).getId(), email);
		} catch (ValidationException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/user/profile";
		}
		redirectAttributes.addFlashAttribute("success", "Email changed successfully");
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/user/profile/changePassword", method = RequestMethod.POST)
	public String userProfileChangePhone(@RequestParam String password, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		try {
			userService.changePassword(((UserDto) authentication.getPrincipal()).getId(), password);
		} catch (ValidationException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/user/profile";
		}
		redirectAttributes.addFlashAttribute("success", "Password changed successfully");
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/user/profile/changePhone", method = RequestMethod.POST)
	public String userProfileChangePassword(@RequestParam String phone, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		try {
			userService.changePhone(((UserDto) authentication.getPrincipal()).getId(), phone);
		} catch (ValidationException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/user/profile";
		}
		redirectAttributes.addFlashAttribute("success", "Phone changed successfully");
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/user/profile/changeImage", method = RequestMethod.POST)
	public String userProfileChangeImage(@RequestParam MultipartFile image, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		try {
			userService.changeImage(((UserDto) authentication.getPrincipal()).getId(), image);
		} catch (ValidationException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/user/profile";
		}
		redirectAttributes.addFlashAttribute("success", "Image changed successfully");
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/user/profile/deleteImage", method = RequestMethod.POST)
	public String userProfileDeleteImage(Authentication authentication, RedirectAttributes redirectAttributes) {
		userService.deleteImage(((UserDto) authentication.getPrincipal()).getId());
		redirectAttributes.addFlashAttribute("success", "Image deleted successfully");
		return "redirect:/user/profile";
	}

}
