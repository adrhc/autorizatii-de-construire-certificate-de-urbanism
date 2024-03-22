package adrhc.go.ro.constructionauth.datasource.pages;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class PageLoader {
    public String load(String urlString) throws IOException {
        URL url = URI.create(urlString).toURL();
        return IOUtils.toString(url, StandardCharsets.UTF_8);
    }
}
