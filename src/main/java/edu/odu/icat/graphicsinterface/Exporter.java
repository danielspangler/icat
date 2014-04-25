package edu.odu.icat.graphicsinterface;
import javax.swing.*;

import java.io.FileOutputStream;
import java.io.File;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Created by AD on 4/22/2014.
 */
public class Exporter {

    private static String Filename = new String();


    public static void exportToPDF(JPanel panel)
    {
       try {
           //File outFile = new File();
           String fileName = new String("test");
           Document document = new Document();
           PdfWriter.getInstance(document, new FileOutputStream(fileName));
       } catch (Exception e){
           e.printStackTrace();
       }
    }
}
