package ro.go.adrhc.constructionauth.datasource.text;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import ro.go.adrhc.constructionauth.datasource.links.LinksExtractor;
import ro.go.adrhc.constructionauth.datasource.links.LinksParser;
import ro.go.adrhc.constructionauth.datasource.links.LinksProviderConfig;
import ro.go.adrhc.constructionauth.lib.PdfTextExtractor;
import ro.go.adrhc.constructionauth.lib.URLContentReader;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@EnableConfigurationProperties
@SpringBootTest(classes = {URLContentReader.class, LinksParser.class, LinksExtractor.class,
		LinksProviderConfig.class, PdfTextExtractor.class, UrlContentProvider.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class UrlContentProviderIT {
	@Autowired
	private UrlContentProvider urlContentProvider;

	@Test
	void load() {
		Optional<UrlContent> urlContentOptional = urlContentProvider.load().findAny();
		assertThat(urlContentOptional).isNotEmpty();
		UrlContent urlContent = urlContentOptional.get();
		log.info("\n{}:\n{}", urlContent.url(), urlContent.text());
	}
}