package ro.go.adrhc.constructionauth.datasource.text;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.go.adrhc.constructionauth.datasource.links.LinksProvider;
import ro.go.adrhc.constructionauth.lib.PdfTextExtractor;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class UrlContentProvider {
	private final Collection<LinksProvider> linkProviders;
	private final PdfTextExtractor pdfTextExtractor;

	public Stream<UrlContent> load() {
		return load(_ -> true);
	}

	public Stream<UrlContent> load(Predicate<String> linksFilter) {
		return linkProviders.stream()
				.flatMap(this::loadLinks)
				.filter(linksFilter)
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
