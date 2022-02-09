package model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Este clasa în care se realizează crearea bonului fiscal pentru tranzactia dorită
 */

public class Receipt {
    /**
     * Această metoda crează un pdf unde se va realiza crearea bonului fiscal
     * @param or
     * @param cl
     * @param pr
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public static void pdf(Orders or, Client cl, Product pr) throws FileNotFoundException, DocumentException {
        Document receipt = new Document();
        String name = "receipt";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        name += formatter.format(date) + ".pdf";
        PdfWriter.getInstance(receipt, new FileOutputStream(name));

        receipt.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);

        Anchor chunk1 = new Anchor("Order id: " + or.getId() + "\n", font);
        Anchor chunk2 = new Anchor("Client: " + cl.getName() + ", address: " + cl.getAddress() + " has ordered:", font);
        Anchor chunk3 = new Anchor(pr.getName() + ".........." + pr.getPrice() + " * " + or.getQuantity() + " = " + or.getTotalPrice(), font);
        receipt.add(chunk1);
        receipt.add(Chunk.NEWLINE);
        receipt.add(chunk2);
        receipt.add(Chunk.NEWLINE);
        receipt.add(chunk3);
        receipt.close();
    }
}
