package adrhc.go.ro.constructionauth.datasource.links;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PageLinksProvider {
    private final PageLoader pageLoader;
    private final LinksParser pageParser;

    public Set<String> loadLinks(String urlString) throws IOException {
        String pageText = pageLoader.load(urlString);
        return pageParser.parseLinks(pageText);
    }
}
