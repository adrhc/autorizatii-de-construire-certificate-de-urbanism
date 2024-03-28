package adrhc.go.ro.constructionauth.lib;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@Slf4j
public class URLContentReader {
    public Optional<byte[]> readBytes(String urlString) {
        try {
            URL url = URI.create(urlString).toURL();
            return Optional.of(IOUtils.toByteArray(url));
        } catch (IOException | NullPointerException e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    public Optional<String> readText(String urlString) {
        try {
            URL url = URI.create(urlString).toURL();
            return Optional.of(IOUtils.toString(url, StandardCharsets.UTF_8));
        } catch (IOException | NullPointerException e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
