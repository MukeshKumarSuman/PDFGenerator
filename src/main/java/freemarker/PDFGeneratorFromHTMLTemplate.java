package freemarker;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PDFGeneratorFromHTMLTemplate {
    public static void main(String[] args) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_34);
        cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        // Data model
        Map<String, Object> data = new HashMap<>();
        data.put("title", "My HTML Page");
        data.put("message", "Hello from FreeMarker!");

        FreemarkerRenderingService freemarkerRenderingService =new FreemarkerRenderingService(cfg);
        freemarkerRenderingService.generatePdf(data);
    }
}
