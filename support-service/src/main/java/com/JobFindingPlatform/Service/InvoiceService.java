package com.JobFindingPlatform.Service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.JobFindingPlatform.Entity.Payment;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceService {
	
	public byte[] generateInvoice(Payment pay) {
		try {
			
			Document doc = new Document();
			ByteArrayOutputStream baops = new ByteArrayOutputStream();
			
			PdfWriter.getInstance(doc, baops);
			
			doc.open();
			
			
			Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,16);
			Paragraph title = new Paragraph("Payment Invoice copy",titleFont);
			title.setAlignment(Element.ALIGN_CENTER);
			doc.add(title);
			
			doc.add(new Paragraph("") );
			doc.add(new Paragraph("Invoice ID:" + pay.getTransactionId()));
			doc.add(new Paragraph("User Id :" + pay.getUserId()));
			doc.add(new Paragraph("Plan ID :"+ pay.getPlanId()));
			doc.add(new Paragraph("Amount Paid: Rs " + pay.getAmount()));
			doc.add(new Paragraph("Payment Status:"+ pay.getPaymentStatus()));
			doc.add(new Paragraph("Date :"+pay.getTimeStamp()));
			
			doc.add(new Paragraph("") );
			doc.add(new Paragraph("Thank you for using our service") );
			
			doc.close();
			
			return baops.toByteArray();
			
		} catch (Exception e) {
			throw new RuntimeException("Error generating in Invoice" ,e );
		}
	}
	
}

