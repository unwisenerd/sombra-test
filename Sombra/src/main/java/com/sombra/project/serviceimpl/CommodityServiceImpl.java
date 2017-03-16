package com.sombra.project.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.Cookie;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sombra.project.dao.CommodityDao;
import com.sombra.project.dao.SubCategoryDao;
import com.sombra.project.dto.CommodityDto;
import com.sombra.project.dto.DtoMapper;
import com.sombra.project.entity.Commodity;
import com.sombra.project.service.CommodityService;
import com.sombra.project.validation.CommodityValidator;
import com.sombra.project.validation.ValidationException;

@Service
public class CommodityServiceImpl implements CommodityService {

	@Autowired
	private CommodityDao commodityDao;
	@Autowired
	private SubCategoryDao subCategoryDao;
	@Autowired
	private CommodityValidator commodityValidator;

	public void save(CommodityDto commodityDto) {
		commodityDao.save(DtoMapper.commodityToEntity(commodityDto));
	}

	public void update(CommodityDto commodityDto) {
		commodityDao.update(DtoMapper.commodityToEntity(commodityDto));
	}

	public void delete(int id) {
		commodityDao.delete(id);
		String absolutePath = System.getProperty("catalina.home") + "/resources/commodities/" + id;
		File file = new File(absolutePath);
		if (file.exists()) {
			try {
				FileUtils.cleanDirectory(file);
				file.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public CommodityDto findOne(int id) {
		Commodity commodity = commodityDao.findOne(id);
		if (commodity != null) {
			return DtoMapper.commodityToDto(commodity);
		} else {
			return null;
		}
	}

	public List<CommodityDto> findAll() {
		List<CommodityDto> commoditiesDto = new ArrayList<>();
		for (Commodity commodity : commodityDao.findAll()) {
			commoditiesDto.add(DtoMapper.commodityToDto(commodity));
		}
		return commoditiesDto;
	}

	public List<CommodityDto> findAllWithSubCategory() {
		List<CommodityDto> commoditiesDto = new ArrayList<>();
		List<Commodity> commodities = commodityDao.findAllWithSubCategory();
		for (Commodity commodity : commodities) {
			commoditiesDto.add(DtoMapper.commodityToDto(commodity));
		}
		return commoditiesDto;
	}

	public List<String> findBrands() {
		return commodityDao.findBrands();
	}

	public List<String> findCountries() {
		return commodityDao.findCountries();
	}

	public int findMinimumPrice() {
		return commodityDao.findMinimumPrice();
	}

	public int findMaximumPrice() {
		return commodityDao.findMaximumPrice();
	}

	public List<CommodityDto> findByFilter(String category, String subcategory, String name, String brand,
			String country, String consist, String price, String order, String direction, String count, String page) {
		List<CommodityDto> commoditiesDto = new ArrayList<>();
		List<Commodity> commodities = commodityDao.findByFilter(category, subcategory, name, brand, country, consist,
				price, order, direction, count, page);
		for (Commodity commodity : commodities) {
			commoditiesDto.add(DtoMapper.commodityToDto(commodity));
		}
		return commoditiesDto;
	}

	public int countPagesByFilter(String category, String subCategory, String name, String brand, String country,
			String consist, String price, String count) {
		long countByFilter = commodityDao.countByFilter(category, subCategory, name, brand, country, consist, price);
		int itemCount = 18;
		if (count.matches("^[0-9]+$")) {
			itemCount = Integer.parseInt(count);
		}
		int pagesCount = (int) Math.ceil((double) countByFilter / itemCount);
		return pagesCount == 0 ? 1 : pagesCount;
	}

	public int getCurrentPage(String page) {
		int currentPage = 0;
		if (page.matches("^[0-9]+$")) {
			currentPage = Integer.parseInt(page);
		}
		return currentPage;
	}

	public void editImage(int id, MultipartFile multipartFile) {
		Commodity commodity = commodityDao.findOne(id);
		String originalFileName = multipartFile.getOriginalFilename();
		String relativePath = "/resources/commodities/" + commodity.getId();
		String absolutePath = System.getProperty("catalina.home") + relativePath;
		String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length())
				.toLowerCase();
		String relativeFileName = relativePath + "/img" + fileExtension;
		String absoluteFileName = absolutePath + "/img" + fileExtension;
		commodity.setImage(relativeFileName);
		commodityDao.update(commodity);
		File file = new File(absolutePath);
		try {
			file.mkdirs();
			FileUtils.cleanDirectory(file);
			multipartFile.transferTo(new File(absoluteFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteImage(int id) {
		Commodity commodity = commodityDao.findOne(id);
		commodity.setImage(null);
		commodityDao.update(commodity);
		String absolutePath = System.getProperty("catalina.home") + "/resources/commodities/" + id;
		File file = new File(absolutePath);
		if (file.exists()) {
			try {
				FileUtils.cleanDirectory(new File(absolutePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Cookie addToCart(int id, int quantity, String cart) throws ValidationException {
		commodityValidator.validateQuantity(id, quantity, cart);
		ObjectMapper objectMapper = new ObjectMapper();
		Cookie cookie = new Cookie("cart", "");
		try {
			Map<String, Integer> commodities;
			if (!cart.isEmpty()) {
				commodities = (Map<String, Integer>) objectMapper.readValue(cart, Map.class);
				if (commodities.containsKey(String.valueOf(id))) {
					int value = commodities.get(String.valueOf(id)) + quantity;
					commodities.remove(String.valueOf(id));
					commodities.put(String.valueOf(id), value);
				} else {
					commodities.put(String.valueOf(id), quantity);
				}
			} else {
				commodities = new HashMap<>();
				commodities.put(String.valueOf(id), quantity);
			}
			cookie.setValue(objectMapper.writeValueAsString(commodities));
			cookie.setMaxAge(24 * 60 * 60);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			return cookie;
		} catch (Exception e) {
			return cookie;
		}
	}

	@SuppressWarnings("unchecked")
	public int getCartQuantity(int id, String cart) {
		ObjectMapper objectMapper = new ObjectMapper();
		int cartQuantity = 0;
		try {
			if (!cart.isEmpty()) {
				Map<String, Integer> commodities = (Map<String, Integer>) objectMapper.readValue(cart, Map.class);
				if (commodities.containsKey(String.valueOf(id))) {
					cartQuantity = commodities.get(String.valueOf(id));
				}
			}
			return cartQuantity;
		} catch (Exception e) {
			return cartQuantity;
		}
	}

	@SuppressWarnings("unchecked")
	public Map<CommodityDto, Integer> getCartCommodities(String cart) {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<CommodityDto, Integer> cartCommodities = new TreeMap<>();
		try {
			if (!cart.isEmpty()) {
				Map<String, Integer> commodities = (Map<String, Integer>) objectMapper.readValue(cart, Map.class);
				for (Map.Entry<String, Integer> entry : commodities.entrySet()) {
					CommodityDto commodityDto = DtoMapper
							.commodityToDto(commodityDao.findOne(Integer.parseInt(entry.getKey())));
					cartCommodities.put(commodityDto, entry.getValue());
				}
			}
			return cartCommodities;
		} catch (Exception e) {
			return cartCommodities;
		}
	}

	public Map<String, String> cartCommoditiesToJson(Map<CommodityDto, Integer> commodities) {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> stringCommodities = new TreeMap<>();
		try {
			for (Map.Entry<CommodityDto, Integer> entry : commodities.entrySet()) {
				stringCommodities.put(objectMapper.writeValueAsString(entry.getKey()),
						String.valueOf(entry.getValue()));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return stringCommodities;
	}

	@SuppressWarnings("unchecked")
	public Cookie deleteFromCart(int id, String cart) {
		ObjectMapper objectMapper = new ObjectMapper();
		Cookie cookie = new Cookie("cart", "");
		try {
			if (!cart.isEmpty()) {
				Map<String, Integer> commodities = (Map<String, Integer>) objectMapper.readValue(cart, Map.class);
				if (commodities.containsKey(String.valueOf(id))) {
					commodities.remove(String.valueOf(id));
				}
				cookie.setValue(objectMapper.writeValueAsString(commodities));
				cookie.setMaxAge(24 * 60 * 60);
				cookie.setHttpOnly(true);
				cookie.setPath("/");
			}
			return cookie;
		} catch (Exception e) {
			return cookie;
		}
	}

	public void addFromCsv(MultipartFile multipartFile) {
		CSVParser csvParser;
		try {
			csvParser = new CSVParser(new InputStreamReader(multipartFile.getInputStream()), CSVFormat.EXCEL);
			for (CSVRecord csvRecord : csvParser.getRecords()) {
				Commodity commodity = new Commodity();
				Iterator<String> iterator = csvRecord.iterator();
				commodity.setName(iterator.next());
				commodity.setBrand(iterator.next());
				commodity.setCountry(iterator.next());
				commodity.setConsist(iterator.next());
				try {
					int price = Integer.parseInt(iterator.next());
					commodity.setPrice(price);
				} catch (NumberFormatException e) {
					commodity.setPrice(0);
				}
				try {
					int available = Integer.parseInt(iterator.next());
					commodity.setAvailable(available);
				} catch (NumberFormatException e) {
					commodity.setAvailable(0);
				}
				try {
					int subCategory = Integer.parseInt(iterator.next());
					commodity.setSubCategory(subCategoryDao.findOne(subCategory));
				} catch (NumberFormatException e) {
					commodity.setSubCategory(subCategoryDao.findOne(0));
				}
				commodityDao.save(commodity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
