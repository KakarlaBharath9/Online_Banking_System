package com.banking.services;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.banking.dtos.YearlyStatementResponse;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

@Service
public class YearlyStatementPdfService {
	public byte[] generatePdf(YearlyStatementResponse statement) {
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		
		PdfWriter writer=new PdfWriter(out);
		PdfDocument pdf=new PdfDocument(writer);
		Document document=new Document(pdf);
		
		// title
		document.add(new Paragraph("Yearly Account Statement")
				.setBold()
				.setFontSize(16)
				);
		
		document.add(new Paragraph("Account Number: "+statement.getAccountNumber()));
		document.add(new Paragraph("Year: "+statement.getYear()));
		document.add(new Paragraph("Opening Balance: "+statement.getOpeningBalance()));
		document.add(new Paragraph("Closing Balance: "+statement.getClosingBalance()));
		document.add(new Paragraph("Total Credit: "+statement.getTotalCredit()));
		document.add(new Paragraph("Total Debit: "+statement.getTotalDebit()));
		document.add(new Paragraph("\n"));
		
		//Transaction Table
		Table table=new Table(4);
		table.addCell("Date");
		table.addCell("Type");
		table.addCell("Amount");
		table.addCell("Account");
		
		statement.getTransactions().forEach(tx->{
			table.addCell(tx.getTimestamp().toString());
			table.addCell(tx.getType());
			table.addCell(tx.getAmount().toString());
			table.addCell(tx.getAccountNumber());
		});
		document.add(table);
		document.close();
		
		return out.toByteArray();
	}
}
