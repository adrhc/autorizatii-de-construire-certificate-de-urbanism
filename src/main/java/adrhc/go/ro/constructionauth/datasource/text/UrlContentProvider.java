package adrhc.go.ro.constructionauth.datasource.text;

import adrhc.go.ro.constructionauth.datasource.links.LinksProvider;
import adrhc.go.ro.constructionauth.lib.PdfTextExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class UrlContentProvider {
    private final Collection<LinksProvider> linkProviders;
    private final PdfTextExtractor pdfTextExtractor;

    public Stream<UrlContent> load() {
        return linkProviders.stream()
                .flatMap(this::loadLinks)
                .map(this::extractText)
                .flatMap(Optional::stream);
    }

    private Optional<UrlContent> extractText(String url) {
        return pdfTextExtractor.extractText(url).map(t -> new UrlContent(url, t));
    }

    private Stream<String> loadLinks(LinksProvider linksProvider) {
        try {
            return linksProvider.loadLinks().stream();
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        }
        return Stream.empty();
    }
}
