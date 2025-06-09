package pdfbox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class PDFDemo {
    private static final Logger log = LoggerFactory.getLogger(PDFDemo.class);

    public static void main(String[] args) throws IOException {
        PDDocument document = new PDDocument();
        PDPage firstPage = new PDPage(PDRectangle.A4);
        document.addPage(firstPage);
        int pageWidth = (int) firstPage.getTrimBox().getWidth();
        int pageHeight = (int) firstPage.getTrimBox().getHeight();
        System.out.println(String.format("Page width:%s and height:%s", pageWidth, pageHeight));
        log.info("Page width:{} and height:{}", pageWidth, pageHeight);

        PDPageContentStream contentStream = new PDPageContentStream(document, firstPage);

        // Top shape
        int[][] coordinates = new int[][] {{0, pageHeight}, {200, pageHeight}, {pageWidth *2/3, pageHeight - 80}, {0, pageHeight - 80}};
        Color color1 = new Color(204, 231, 255);
        drawShap(contentStream, coordinates, color1, color1);
        Color aliceBlue = new Color(240, 248, 255);
        coordinates = new int[][] {{200, pageHeight}, {250, pageHeight}, {pageWidth *2/3, pageHeight - 80}};
        drawShap(contentStream, coordinates, aliceBlue, aliceBlue);
        Color darkBlue = new Color(0, 82, 153);
        coordinates = new int[][] {{240, pageHeight - 80}, {pageWidth, pageHeight - 80}, {pageWidth, pageHeight - 90}};
        drawShap(contentStream, coordinates, darkBlue, darkBlue);

        // Bottom shape
        coordinates = new int[][] {{0, 20}, {100, 20}, {80, 0}, {0, 0}};
        drawShap(contentStream, coordinates, darkBlue, darkBlue);
        coordinates = new int[][] {{100, 20}, {pageWidth - 150, 20}, {pageWidth - 170, 0}, {80, 0}};
        drawShap(contentStream, coordinates, color1, color1);
        coordinates = new int[][] {{pageWidth - 150, 20}, {pageWidth - 70, 20}, {pageWidth - 90, 0}, {pageWidth - 170, 0}};
        drawShap(contentStream, coordinates, darkBlue, darkBlue);
        coordinates = new int[][] {{pageWidth - 70, 20}, {pageWidth - 50, 20}, {pageWidth - 70, 0}, {pageWidth - 90, 0}};
        drawShap(contentStream, coordinates, color1, color1);
        Color orange = new Color(255, 128, 0);
        coordinates = new int[][] {{pageWidth - 50, 20}, {pageWidth - 20, 20}, {pageWidth - 40, 0}, {pageWidth - 70, 0}};
        drawShap(contentStream, coordinates, orange, orange);
        coordinates = new int[][] {{pageWidth - 20, 20}, {pageWidth, 20}, {pageWidth - 20, 0}, {pageWidth - 40, 0}};
        drawShap(contentStream, coordinates, color1, color1);
        coordinates = new int[][] {{pageWidth, 20}, {pageWidth, 0}, {pageWidth - 20, 0}};
        drawShap(contentStream, coordinates, darkBlue, darkBlue);

        // 1st Information
        Color infoBoxColor = new Color(230, 243, 255);
        coordinates = new int[][] {{20, pageHeight - 100}, {pageWidth - 20, pageHeight - 100}, {pageWidth - 20, pageHeight - 130}, {20, pageHeight - 130}};
        drawShap(contentStream, coordinates, infoBoxColor, infoBoxColor);
        PDFTextWriter textWriter = new PDFTextWriter(document, contentStream);
        PDFont font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        textWriter.addSingleLineText("Primary Contact Information", 40, pageHeight - 120, font, 20, darkBlue);

        contentStream.close();
        document.save("demo.pdf"); // This will create a pdf file in root project.
//        document.save("C:\\Users\\Lenovo\\Desktop\\demo.pdf"); // This will create a pdf file in Desktop.
        document.close();
        System.out.println("PDF created!");
        log.info("PDF Created");
    }

    private static void drawShap(PDPageContentStream contentStream, int[][] coordinates, Color strokingColor, Color fillColor) throws IOException {
        // Set line width and color
        contentStream.setLineWidth(0);
        contentStream.setStrokingColor(strokingColor);
        if (fillColor != null) {
            contentStream.setNonStrokingColor(fillColor);
        }
        // Begin drawing
//        contentStream.moveTo(0, pageHeight); // First point
//        contentStream.lineTo(200, pageHeight); // Second point
//        contentStream.lineTo(pageWidth *2/3, pageHeight - 60); // Third point
//        contentStream.lineTo(0, pageHeight - 60); // Fourth point
        for (int i = 0; i < coordinates.length; i++) {
            if (i == 0) {
                contentStream.moveTo(coordinates[i][0], coordinates[i][1]);
            } else {
                contentStream.lineTo(coordinates[i][0], coordinates[i][1]);
            }
        }
        contentStream.closePath(); // Closes the path back to (x1, y1)
        if (fillColor == null) {
            contentStream.stroke(); // Outline the quadrilateral
        } else {
            contentStream.fillAndStroke();
        }
    }

    private static void drawLine(int pageWidth, int pageHeight, PDPageContentStream contentStream) throws IOException {
        contentStream.setStrokingColor(Color.BLACK);
        contentStream.setLineWidth(2);
        contentStream.moveTo(pageWidth - 250, pageHeight - 50); // // Start point (x, y)
        contentStream.lineTo(pageWidth - 25, pageHeight - 50); // End point (x, y)
        contentStream.stroke(); // draw the line
    }

    private static void createTable(int pageHeight, PDPageContentStream contentStream) throws IOException {
        PDFont font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        // add table
        int cellHeight = 30;
        int cellWidth = 100;
        int x = 50;
        int y = pageHeight - 50;
        int xInitial = x;
        int colCount = 5;
        int rowCount = 4;

        for (int i = 1; i <= rowCount; i++) {
            for (int j = 1; j <= colCount; j++) {
                contentStream.setStrokingColor(Color.DARK_GRAY);
                contentStream.setNonStrokingColor(Color.GREEN);
                contentStream.setLineWidth(2);
                // As we are moving from top to bottom so cell height will be -ve.
                contentStream.addRect(x, y, cellWidth, -cellHeight);
                contentStream.fillAndStroke(); // For fill the cell with color and outline
                contentStream.beginText();
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.newLineAtOffset(x + 10, y - cellHeight + 10);
                contentStream.setFont(font, 20);
                contentStream.showText("Text-1");
                contentStream.endText();

                x = x + cellWidth;
            }
            x = xInitial;
            y = y - cellHeight;
        }
    }

    private static void addBulletList(PDPageContentStream contentStream) throws IOException {
        PDFont font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        // Add bullet list
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.setLeading(20f); // Line spacing
        contentStream.newLineAtOffset(50, 700); // Start position

        List<String> items = List.of("First item", "Second item", "Third item");

        for (String item : items) {
            contentStream.showText("\u2022 " + item); // Unicode bullet character
            contentStream.newLine();
        }
        contentStream.endText();
    }

    private static void addImage(PDDocument document, int pageWidth, int pageHeight, PDPageContentStream contentStream) throws IOException {
        // Add image
        PDImageXObject headImage = PDImageXObject.createFromFile("src/main/resources/img/Indian Tadka head.png", document);
        contentStream.drawImage(headImage, 150, pageHeight - 200, pageWidth - 300, 150);
    }

    private static void addText(int pageHeight, PDPageContentStream contentStream) throws IOException {
        // add text to pdf
        PDFont fontRoman = new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);
        contentStream.beginText();
        ;
        contentStream.setFont(fontRoman, 18);
        contentStream.setLeading(56.0f);// space between two line of text
        // Page will always start from bottom left corner(x = 0, y = 0)
        contentStream.newLineAtOffset(25, pageHeight - 50); // coordinate from where text will start.
        contentStream.setNonStrokingColor(Color.BLUE); // Set the text color
        contentStream.showText("This is the first line");
        contentStream.newLine();
        contentStream.showText("This is the second line");
        contentStream.endText();
    }

    private static void addDocInfo(PDDocument document) {
        // Add PDF Properties
        PDDocumentInformation docInfo = document.getDocumentInformation();
        docInfo.setAuthor("Mukesh");
        docInfo.setTitle("Demo PDF File");
        docInfo.setCreationDate(Calendar.getInstance());
    }
}
