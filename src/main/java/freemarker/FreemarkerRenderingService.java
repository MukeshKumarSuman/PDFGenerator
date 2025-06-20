package freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;

public class FreemarkerRenderingService {
    private final Configuration freemarkerConfiguration;

    public FreemarkerRenderingService(Configuration freemarkerConfiguration) {
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    public void generatePdf(Map<String, Object> data) throws TemplateException, IOException {
        String content = render("moxo.ftl", data);
    }

    private String render(String templateName, Map<String, Object> dataModel) throws IOException, TemplateException {
        Objects.requireNonNull(templateName, "templateName");
        Objects.requireNonNull(dataModel, "model");

        Template template = loadTemplate(templateName);
        String html = processTemplate(template, dataModel);
        System.out.println(html);
        htmlToPdf(html);
//        openHtmlToPdf(html);
        return html;
    }

    private Template loadTemplate(String templateName) throws IOException {
        return freemarkerConfiguration.getTemplate(templateName);
    }

    public static String processTemplate(Template template, Map<String, Object> dataModel) throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        template.process(dataModel, writer);
        return writer.toString();
    }

    private void htmlToPdf(String content) throws IOException {
        System.out.println("HTML Content");
        System.out.println(content);

//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        OutputStream outputStream = new FileOutputStream("MoxoPdfFromHtml.pdf");

        ITextRenderer iTextRenderer = new ITextRenderer();
        iTextRenderer.getSharedContext().getTextRenderer().setSmoothingThreshold(0f);
        iTextRenderer.setDocumentFromString(content);
        iTextRenderer.layout();
        iTextRenderer.createPDF(outputStream);
        outputStream.close();


//
//        iTextRenderer.createPDF(byteArrayOutputStream);
//        byte[] byteArray = byteArrayOutputStream.toByteArray();
//        System.out.println(byteArray);

    }

    private void openHtmlToPdf(String htmlContent) {
//        try (OutputStream os = new FileOutputStream("openhtmltopdf.pdf")) {
//            PdfRendererBuilder builder = new PdfRendererBuilder();
//            builder.useFastMode(); // Optional: improves speed
//            builder.withHtmlContent(htmlContent, null); // base URI = null (no external resources)
//            builder.toStream(os);
//            builder.run();
//            System.out.println("PDF created successfully.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
