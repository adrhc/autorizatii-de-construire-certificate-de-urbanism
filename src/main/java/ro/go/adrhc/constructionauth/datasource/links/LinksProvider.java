package adrhc.go.ro.constructionauth.datasource.links;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
public class LinksProvider {
    private final String url;
    private final LinksExtractor linksExtractor;

    public Set<String> loadLinks() throws IOException {
        return linksExtractor.loadLinks(url);
    }
}
