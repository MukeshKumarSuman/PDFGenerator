package pdfbox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.Color;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PDFBoxPDFGenerator {
    public static void main(String[] args) {
        try {
            PDDocument document = new PDDocument();
            PDPage firstPage = new PDPage(PDRectangle.A4);
            document.addPage(firstPage);

            String workflowId = "BUw7XDey7ZnKLtIsKnORe8E";
            String customerName = "Samsung";
            String primaryContactName = "Mukesh Kumar Suman";
            String primaryContactNumber = "+918787878788";
            String primaryContactEmailNumber = "mukesh@gamil.com";

            String companyControllerName = "Abhishek Kumar";
            String companyControllerEmailAddress  = "abhishek@gamil.com";

            String parentCompanyName = "Mineral Tree";
            String companyDescription = "Working in invoice payment";
            String companyPhoneNumber = "+918787878788";

            Format dFormat = new SimpleDateFormat("dd/MM/yyyy");
            Format tFormat = new SimpleDateFormat("HH:mm");

            int pageWidth = (int) firstPage.getTrimBox().getWidth();
            int pageHeight = (int) firstPage.getTrimBox().getHeight();

            PDPageContentStream contentStream = new PDPageContentStream(document, firstPage);
            PDFTextWriter pdfTextWriter = new PDFTextWriter(document, contentStream);

            PDFont font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            PDFont italicFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE);

//            PDImageXObject headImage = PDImageXObject.createFromFile("src/main/resources/img/Indian Tadka head.png", document);
            PDImageXObject headImage = PDImageXObject.createFromFile("src/main/resources/img/mineraltree.png", document);
            contentStream.drawImage(headImage, 150, pageHeight - 150, pageWidth - 300, 150);

            String[] contactDetails = new String[]{"+919876543268", "+919876543268"};
            pdfTextWriter.addMultiLineText(contactDetails, 18, (int)(pageWidth - font.getStringWidth("+919876543268")/1000*15 - 10),
                    pageHeight - 25, font, 15, Color.BLACK);
            pdfTextWriter.addSingleLineText(parentCompanyName, 25, pageHeight - 150, font, 30, Color.BLUE);

            int contentFontSize = 12;
            // Left align
            pdfTextWriter.addSingleLineText("Customer Name: " + customerName, 25, pageHeight - 200, font, contentFontSize, Color.BLACK);
            pdfTextWriter.addSingleLineText("Primary Contact Number : " + primaryContactNumber, 25, pageHeight - 225, font, contentFontSize, Color.BLACK);

            // Right align
            float workflowIdTextWidth = pdfTextWriter.getTextWidth(workflowId, font, contentFontSize);
            System.out.println("workflowIdTextWidth: " + workflowIdTextWidth);
            pdfTextWriter.addSingleLineText("Workflow Id : " + workflowId, (int)(pageWidth - 80 - workflowIdTextWidth), pageHeight - 200, font, contentFontSize, Color.BLACK);

            float dateTextWidth = pdfTextWriter.getTextWidth("Date: " + dFormat.format(new Date()), font, contentFontSize);
            pdfTextWriter.addSingleLineText("Date: " + dFormat.format(new Date()), (int) (pageWidth - 10 - dateTextWidth), pageHeight - 225, font, contentFontSize, Color.BLACK);

            String time = tFormat.format(new Date());
            float timeTextWidth = pdfTextWriter.getTextWidth("Time: " + time, font, contentFontSize);
            pdfTextWriter.addSingleLineText("Time: " + time, (int) (pageWidth - 10 - timeTextWidth), pageHeight - 250, font, contentFontSize, Color.BLACK);

            PDFTableWriter pdfTableWriter = new PDFTableWriter(document, contentStream);
            int[] cellWidth = {100, 100, 100, 100, 100};
            pdfTableWriter.setTable(cellWidth, 30, 25, pageHeight - 300);
            pdfTableWriter.setTableFont(font, contentFontSize, Color.BLACK);

            Color tableHeaderColor = new Color(240, 93, 11);
            Color tableBodyColor = new Color(219, 218, 198);

            pdfTableWriter.addCell("Si.No.", tableHeaderColor);
            pdfTableWriter.addCell("Item", tableHeaderColor);
            pdfTableWriter.addCell("Price", tableHeaderColor);
            pdfTableWriter.addCell("Qty", tableHeaderColor);
            pdfTableWriter.addCell("Total", tableHeaderColor);

            pdfTableWriter.addCell("1", tableBodyColor);
            pdfTableWriter.addCell("Mouse", tableBodyColor);
            pdfTableWriter.addCell("200$", tableBodyColor);
            pdfTableWriter.addCell("2", tableBodyColor);
            pdfTableWriter.addCell("400$", tableBodyColor);

            pdfTableWriter.addCell("2", tableBodyColor);
            pdfTableWriter.addCell("Mac", tableBodyColor);
            pdfTableWriter.addCell("1000$", tableBodyColor);
            pdfTableWriter.addCell("10", tableBodyColor);
            pdfTableWriter.addCell("10000$", tableBodyColor);

            String[] paymentMethod = {"Mode of payment we accepts:", "CASH, ACH, Visa, Mastercard and Check"};
            pdfTextWriter.addMultiLineText(paymentMethod, 15, 25, 180, italicFont, 10, new Color(122, 122, 122));

            // Authorized Signature
            contentStream.setStrokingColor(Color.BLACK);
            contentStream.setLineWidth(2);
            contentStream.moveTo(pageWidth - 250, 150);
            contentStream.lineTo(pageWidth - 25, 150);
            contentStream.stroke();

            String authSign = "Authorized Signature";
            float authSignTextWidth = pdfTextWriter.getTextWidth(authSign, italicFont, contentFontSize);
            int xpos = pageWidth - 250 + pageWidth - 25;
            pdfTextWriter.addSingleLineText(authSign, (int)(xpos - authSignTextWidth)/2, 125, italicFont, contentFontSize, Color.BLACK);

            String bottomLine = "MineralTree is the owner of all intellectual property rights for MineralTree TotalAP and MineralTree TotalPay. Intellectual property rights to the other products are held by their respective owners.";
            float bottomLineTextWidth = pdfTextWriter.getTextWidth(bottomLine, italicFont, 10);
            pdfTextWriter.addSingleLineText(bottomLine, (int)(pageWidth - bottomLineTextWidth)/2, 50, italicFont, 10, Color.BLACK);

            Color bottomRectColor = new Color(255, 91, 0);
            contentStream.setNonStrokingColor(bottomRectColor);
            contentStream.addRect(0, 0, pageWidth, 30);
            contentStream.fill();

            contentStream.close();
            System.out.println(pageWidth);
            System.out.println(pageHeight);

            document.save("Moxo.pdf");
            document.close();
            System.out.println("PDF Created");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
