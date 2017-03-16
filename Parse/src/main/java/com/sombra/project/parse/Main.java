package com.sombra.project.parse;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	public static void main(String[] args) throws IOException {
		String catalinaHome = "D:/Install/eclipse_ee/plugins/apache-tomcat-8.0.33/";
		int commoditiesToGet = 20;
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Sombra");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Category[] categories = new Category[2];
		categories[0] = new Category("Women");
		categories[1] = new Category("Men");

		SubCategory[] subCategories = new SubCategory[17];
		subCategories[0] = new SubCategory("Dresses", categories[0]);
		subCategories[1] = new SubCategory("Outerwear", categories[0]);
		subCategories[2] = new SubCategory("Footwear", categories[0]);
		subCategories[3] = new SubCategory("Bags", categories[0]);
		subCategories[4] = new SubCategory("Blouses and shirts", categories[0]);
		subCategories[5] = new SubCategory("Skirts", categories[0]);
		subCategories[6] = new SubCategory("Pants", categories[0]);
		subCategories[7] = new SubCategory("T-Shirts", categories[0]);
		subCategories[8] = new SubCategory("Underwear", categories[0]);
		subCategories[9] = new SubCategory("Outerwear", categories[1]);
		subCategories[10] = new SubCategory("Footwear", categories[1]);
		subCategories[11] = new SubCategory("Sweaters", categories[1]);
		subCategories[12] = new SubCategory("Shirts", categories[1]);
		subCategories[13] = new SubCategory("Pants", categories[1]);
		subCategories[14] = new SubCategory("Jeans", categories[1]);
		subCategories[15] = new SubCategory("T-Shirts", categories[1]);
		subCategories[16] = new SubCategory("Underwear", categories[1]);

		for (Category category : categories) {
			entityManager.getTransaction().begin();
			entityManager.persist(category);
			entityManager.getTransaction().commit();
			System.err.println(category);
		}

		for (SubCategory subCategory : subCategories) {
			entityManager.getTransaction().begin();
			entityManager.persist(subCategory);
			entityManager.getTransaction().commit();
			System.err.println(subCategory);
		}

		Document document = Jsoup.connect("https://leboutique.com/").maxBodySize(0).timeout(0).get();

		Element womenSubCategoryList = document.select("li#women_menu .b-menu-content .top-categories").get(0);
		Elements womenSubCategoryAnchors = womenSubCategoryList.select("a");
		String womenSubCategoryLinks[] = new String[womenSubCategoryAnchors.size() - 1];
		for (int i = 0; i < womenSubCategoryAnchors.size() - 1; i++) {
			womenSubCategoryLinks[i] = womenSubCategoryAnchors.get(i).attr("abs:href");
		}
		for (int i = 0; i < womenSubCategoryLinks.length; i++) {
			Document womenSubCategory = Jsoup.connect(womenSubCategoryLinks[i]).maxBodySize(0).timeout(0).get();
			Elements womenCommodityAnchors = womenSubCategory.select("div.desc div.tt a:eq(0)");
			String womenCommodityLinks[] = new String[womenCommodityAnchors.size()];
			for (int j = 0; j < womenCommodityAnchors.size(); j++) {
				womenCommodityLinks[j] = womenCommodityAnchors.get(j).attr("abs:href");
			}
			int womenCommodityCount = 0;
			for (int j = 0; j < womenCommodityLinks.length; j++) {
				if (womenCommodityCount >= commoditiesToGet) {
					break;
				}
				Document womenCommodity = Jsoup.connect(womenCommodityLinks[j]).maxBodySize(0).timeout(0).get();

				Elements womenCommodityName = womenCommodity.select("h1.js-product-name");
				if (womenCommodityName.isEmpty()) {
					continue;
				}
				String name = womenCommodityName.get(0).html();

				Elements womenCommodityBrand = womenCommodity.select("span.p-name:containsOwn(Бренд)");
				if (womenCommodityBrand.isEmpty()) {
					continue;
				}
				String brand = womenCommodityBrand.get(0).nextElementSibling().html();

				Elements womenCommodityCountry = womenCommodity.select("span.p-name:containsOwn(Страна)");
				if (womenCommodityCountry.isEmpty()) {
					continue;
				}
				String country = womenCommodityCountry.get(0).nextElementSibling().html();

				Elements womenCommodityConsist = womenCommodity.select("span.p-name:containsOwn(Состав)");
				if (womenCommodityConsist.isEmpty()) {
					continue;
				}
				String consist = womenCommodityConsist.get(0).nextElementSibling().html();

				Elements womenCommodityPrice = womenCommodity.select("span.js-current-price");
				if (womenCommodityPrice.isEmpty()) {
					continue;
				}
				int price = Integer.valueOf(womenCommodityPrice.get(0).html().replaceAll("[^\\d]", ""));

				Random random = new Random();
				int available = random.nextInt(50);

				Commodity commodity = new Commodity(name, brand, country, consist, price, available, subCategories[i]);
				entityManager.getTransaction().begin();
				entityManager.persist(commodity);
				entityManager.getTransaction().commit();

				Elements womenCommodityImage = womenCommodity.select("img.js-product-photo:eq(0)");
				if (!womenCommodityImage.isEmpty()) {
					String womenCommodityImageLink = womenCommodityImage.get(0).attr("src");
					BufferedImage bufferedImage = ImageIO.read(new URL(womenCommodityImageLink));
					File file = new File(catalinaHome + "/resources/commodities/" + commodity.getId() + "/img.jpg");
					file.mkdirs();
					ImageIO.write(bufferedImage, "jpg", file);
					commodity.setImage("/resources/commodities/" + commodity.getId() + "/img.jpg");

					entityManager.getTransaction().begin();
					entityManager.merge(commodity);
					entityManager.getTransaction().commit();
				}

				System.err.println(commodity);
				womenCommodityCount++;
			}
		}
		
		Element menSubCategoryList = document.select("li#men_menu .b-menu-content .top-categories").get(0);
		Elements menSubCategoryAnchors = menSubCategoryList.select("a");
		String menSubCategoryLinks[] = new String[menSubCategoryAnchors.size() - 1];
		for (int i = 0; i < menSubCategoryAnchors.size() - 1; i++) {
			menSubCategoryLinks[i] = menSubCategoryAnchors.get(i).attr("abs:href");
		}
		for (int i = 0; i < menSubCategoryLinks.length; i++) {
			Document menSubCategory = Jsoup.connect(menSubCategoryLinks[i]).maxBodySize(0).timeout(0).get();
			Elements menCommodityAnchors = menSubCategory.select("div.desc div.tt a:eq(0)");
			String menCommodityLinks[] = new String[menCommodityAnchors.size()];
			for (int j = 0; j < menCommodityAnchors.size(); j++) {
				menCommodityLinks[j] = menCommodityAnchors.get(j).attr("abs:href");
			}
			int menCommodityCount = 0;
			for (int j = 0; j < menCommodityLinks.length; j++) {
				if (menCommodityCount >= commoditiesToGet) {
					break;
				}
				Document menCommodity = Jsoup.connect(menCommodityLinks[j]).maxBodySize(0).timeout(0).get();

				Elements menCommodityName = menCommodity.select("h1.js-product-name");
				if (menCommodityName.isEmpty()) {
					continue;
				}
				String name = menCommodityName.get(0).html();

				Elements menCommodityBrand = menCommodity.select("span.p-name:containsOwn(Бренд)");
				if (menCommodityBrand.isEmpty()) {
					continue;
				}
				String brand = menCommodityBrand.get(0).nextElementSibling().html();

				Elements menCommodityCountry = menCommodity.select("span.p-name:containsOwn(Страна)");
				if (menCommodityCountry.isEmpty()) {
					continue;
				}
				String country = menCommodityCountry.get(0).nextElementSibling().html();

				Elements menCommodityConsist = menCommodity.select("span.p-name:containsOwn(Состав)");
				if (menCommodityConsist.isEmpty()) {
					continue;
				}
				String consist = menCommodityConsist.get(0).nextElementSibling().html();

				Elements menCommodityPrice = menCommodity.select("span.js-current-price");
				if (menCommodityPrice.isEmpty()) {
					continue;
				}
				int price = Integer.valueOf(menCommodityPrice.get(0).html().replaceAll("[^\\d]", ""));

				Random random = new Random();
				int available = random.nextInt(50);

				Commodity commodity = new Commodity(name, brand, country, consist, price, available, subCategories[i+womenSubCategoryLinks.length]);
				entityManager.getTransaction().begin();
				entityManager.persist(commodity);
				entityManager.getTransaction().commit();

				Elements menCommodityImage = menCommodity.select("img.js-product-photo:eq(0)");
				if (!menCommodityImage.isEmpty()) {
					String menCommodityImageLink = menCommodityImage.get(0).attr("src");
					BufferedImage bufferedImage = ImageIO.read(new URL(menCommodityImageLink));
					File file = new File(catalinaHome + "/resources/commodities/" + commodity.getId() + "/img.jpg");
					file.mkdirs();
					ImageIO.write(bufferedImage, "jpg", file);
					commodity.setImage("/resources/commodities/" + commodity.getId() + "/img.jpg");

					entityManager.getTransaction().begin();
					entityManager.merge(commodity);
					entityManager.getTransaction().commit();
				}

				System.err.println(commodity);
				menCommodityCount++;
			}
		}
	}
}
