package ro.go.adrhc.constructionauth.datasource.links;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.go.adrhc.constructionauth.lib.URLContentReader;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class LinksExtractor {
    private final URLContentReader urlContentReader;
    private final LinksParser pageParser;

    public Set<String> loadLinks(String urlString) throws IOException {
        return urlContentReader
                .readText(urlString)
                .map(pageParser::parseLinks)
                .orElseGet(Set::of);
    }
}
