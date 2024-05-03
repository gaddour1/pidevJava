package utils;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import entities.visite;

public class PDFGenerator {

    public static void generateVisitReport(visite visit, double cout ,String dest) throws Exception {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        // Ajout de contenu au document
        document.add(new Paragraph("Rapport de Visite")
                .setFont(boldFont)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("Date de visite: " + visit.getDate())
                .setFont(normalFont)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("Heure de visite: " + visit.getHeure())
                .setFont(normalFont)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("Lieu de visite: " + visit.getLieu())
                .setFont(normalFont)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("Traitement: " + visit.getTraitement().getNom())
                .setFont(normalFont)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Coût de la visite: " + String.format("%.2f TND", cout))
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(12)
                 .setTextAlignment(TextAlignment.CENTER));

        // Espace pour la signature
        document.add(new Paragraph("\nSignature de l'infirmier:")
                .setFont(normalFont)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.RIGHT));

        // Ajout d'une ligne horizontale pour la signature
        document.add(new Paragraph("_____________________________________________")
                .setTextAlignment(TextAlignment.RIGHT));

        document.add(new Paragraph("Nom de l'infirmier:")
                .setFont(normalFont)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.RIGHT));

        // Fermeture du document
        document.close();
    }
}
