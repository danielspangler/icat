package edu.odu.icat.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.FileOutputStream;
/**
 * Export Centrality Reports into PDF files
 */

/**This is just a rough draft. Waiting for Mr.Overachiever finishes the Analytic Engine*/

public class ExportPDF {
    public void CreatePDF (){
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream("ICAT.pdf"));
            document.open();
            document.newPage();
            Font font = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
            Font font1 = FontFactory.getFont("Times-Roman", 12, BaseColor.ORANGE);

            //Paragraph paragraph = new Paragraph();
            document.add(new Paragraph("If you're reading this, then you're in the wrong place!", font1));
            document.add(new Paragraph("Dr. Hester is Awesome :)", font));
////
////            PdfPTable table = new PdfPTable(2); /*Number of Columns*/
////
////            PdfPCell cell1 = new PdfPCell(new Paragraph("Cell one"));
////            PdfPCell cell2 = new PdfPCell(new Paragraph("Cell 2"));
////
////            table.addCell(cell1);
////            table.addCell(cell2);
////
////            document.add(table);
            document.close();

            //table.addCell("cell one");

        } catch (Exception e){
            e.printStackTrace();
        }




    }

    public void JpanelPDF(){
        Document document = new Document(PageSize.A4);
        try{
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("ICATPanel.pdf"));
            document.open();
            PdfContentByte contentByte = pdfWriter.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(500, 500);
            Graphics2D graphics2D = template.createGraphics(500, 500);
            

            graphics2D.dispose();
            contentByte.addTemplate(template, 30, 300);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void CentralityReport(String reportType, String header){
        Document document = new Document(PageSize.A4);
        try{
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(".pdf"));
            document.open();
            Phrase [] Header = new Phrase[2];
            /** Current page number **/
            int pageNumber;

            Header [0] = new Phrase(header);

            pageNumber = 1;

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
