package utils;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import entities.visite;

public class PDFGenerator {

    public static void generateVisitReport(visite visit, String dest) throws Exception {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Rapport de Visite")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("Date de visite: " + visit.getDate())
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(12));

        document.add(new Paragraph("Heure de visite: " + visit.getHeure())
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(12));

        document.add(new Paragraph("Lieu de visite: " + visit.getLieu())
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(12));

        document.add(new Paragraph("Traitement: " + visit.getTraitement().getNom())
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(12));

        document.close();
    }
}
