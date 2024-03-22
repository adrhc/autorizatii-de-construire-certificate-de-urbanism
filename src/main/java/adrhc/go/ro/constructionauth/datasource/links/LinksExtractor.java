package adrhc.go.ro.constructionauth.datasource.links;

import adrhc.go.ro.constructionauth.lib.URLContentReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
