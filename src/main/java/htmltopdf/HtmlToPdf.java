package htmltopdf;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.extend.FontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;

public class HtmlToPdf {
    private static final String NPS_HTML_PAGE = "nps.html";
    public static void main(String[] args) throws Exception {
        String html = HtmlTemplate.getMoxoTemplate(NPS_HTML_PAGE);

        String parseHtml = htmlToXhtml(html);
        System.out.println(parseHtml);

        xhtmlToPdf(parseHtml);
        xhtmlToPdfMultiPage(parseHtml);
    }

    private static void xhtmlToPdfMultiPage(String parseHtml) throws IOException {
        // Set base URI to resource path
        String baseUri = Paths.get("src/main/resources/img/").toUri().toString();  // important!
        File output = new File("multiPageNps.pdf");
        OutputStream os = new FileOutputStream(output);
        ITextRenderer iTextRenderer = new ITextRenderer();
        FontResolver resolver = iTextRenderer.getFontResolver();
//        iTextRenderer.getFontResolver().addFont("MyFont.ttf", true);
        iTextRenderer.setDocumentFromString(parseHtml, baseUri);
        iTextRenderer.layout();
        iTextRenderer.createPDF(os, false);
        for (int i = 1; i < 5; i++) {
            iTextRenderer.setDocumentFromString(parseHtml, baseUri);
            iTextRenderer.layout();
            iTextRenderer.writeNextDocument();
        }
        iTextRenderer.finishPDF();
        os.close();
    }

    private static void xhtmlToPdf(String parseHtml) throws IOException {
        // Set base URI to resource path
        String baseUri = Paths.get("src/main/resources/img/").toUri().toString();  // important!
        File output = new File("Nps.pdf");
        ITextRenderer iTextRenderer = new ITextRenderer();
        FontResolver resolver = iTextRenderer.getFontResolver();
//        iTextRenderer.getFontResolver().addFont("MyFont.ttf", true);
        iTextRenderer.setDocumentFromString(parseHtml, baseUri);
        iTextRenderer.layout();
        OutputStream os = new FileOutputStream(output);
        iTextRenderer.createPDF(os);
        os.close();
    }

    private static String htmlToXhtml(String html) {
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }


}
