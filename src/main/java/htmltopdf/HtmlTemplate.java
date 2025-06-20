package htmltopdf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HtmlTemplate {
    private static String getHTMLDetails(String fileName) {
        try(InputStream ioStream = HtmlTemplate.class.getClassLoader()
                .getResourceAsStream(String.format("templates/%s", fileName))) {
            if (ioStream == null) {
                throw new RuntimeException("Resource not found");
            }
            return new String(ioStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static String getMoxoTemplate(String htmlPage) {
        return getHTMLDetails(htmlPage);
    }

}
