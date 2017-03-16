package com.sombra.project.serviceimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sombra.project.dto.OrderDto;
import com.sombra.project.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

	public void createDocument(OrderDto orderDto, String fontUri) {
		Document document = new Document();
		if (fontUri.isEmpty()) {
			fontUri = FontFactory.TIMES_ROMAN;
			System.out.println(fontUri + "Failed");
		}
		Font headerFont = FontFactory.getFont(fontUri, BaseFont.IDENTITY_H, true, 24, Font.NORMAL);
		Font contentFont = FontFactory.getFont(fontUri, BaseFont.IDENTITY_H, true, 14, Font.NORMAL);
		Font dateFont = FontFactory.getFont(fontUri, BaseFont.IDENTITY_H, true, 12, Font.NORMAL);
		Paragraph header = new Paragraph();
		header.add(new Chunk("Payment Check #" + orderDto.getId(), headerFont));
		header.setAlignment(Element.ALIGN_CENTER);
		PdfPTable table = new PdfPTable(3);
		PdfPCell commodityName = new PdfPCell(new Phrase(new Chunk("Commodity:", contentFont)));
		commodityName.setBorder(Rectangle.NO_BORDER);
		PdfPCell commodityValue = new PdfPCell(new Phrase(new Chunk(orderDto.getCommodity().getName(), contentFont)));
		commodityValue.setBorder(Rectangle.NO_BORDER);
		commodityValue.setColspan(2);
		table.addCell(commodityName);
		table.addCell(commodityValue);
		PdfPCell quantityName = new PdfPCell(new Phrase(new Chunk("Quantity:", contentFont)));
		quantityName.setBorder(Rectangle.NO_BORDER);
		PdfPCell quantityValue = new PdfPCell(
				new Phrase(new Chunk(String.valueOf(orderDto.getQuantity()), contentFont)));
		quantityValue.setBorder(Rectangle.NO_BORDER);
		quantityValue.setColspan(2);
		table.addCell(quantityName);
		table.addCell(quantityValue);
		PdfPCell priceName = new PdfPCell(new Phrase(new Chunk("Price:", contentFont)));
		priceName.setBorder(Rectangle.NO_BORDER);
		PdfPCell priceValue = new PdfPCell(new Phrase(new Chunk(
				String.valueOf("$" + orderDto.getQuantity() * orderDto.getCommodity().getPrice()), contentFont)));
		priceValue.setBorder(Rectangle.NO_BORDER);
		priceValue.setColspan(2);
		table.addCell(priceName);
		table.addCell(priceValue);
		PdfPCell cardName = new PdfPCell(new Phrase(new Chunk("Card:", contentFont)));
		cardName.setBorder(Rectangle.NO_BORDER);
		PdfPCell cardValue = new PdfPCell(new Phrase(new Chunk(orderDto.getCard(), contentFont)));
		cardValue.setBorder(Rectangle.NO_BORDER);
		cardValue.setColspan(2);
		table.addCell(cardName);
		table.addCell(cardValue);
		Paragraph date = new Paragraph();
		date.add(new Chunk(new Date().toString(), dateFont));
		date.setAlignment(Element.ALIGN_CENTER);
		try {
			File folder = new File(System.getProperty("catalina.home") + "/resources/orders/" + orderDto.getId());
			folder.mkdirs();
			File file = new File(folder.getAbsolutePath() + "/" + orderDto.getDocument() + ".pdf");
			file.createNewFile();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			document.add(header);
			document.add(new Paragraph("\n"));
			document.add(table);
			document.add(new Paragraph("\n"));
			document.add(date);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
