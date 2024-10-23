package ro.go.adrhc.constructionauth.datasource.text;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import ro.go.adrhc.constructionauth.ConstructionAuthConfig;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@EnableConfigurationProperties
@SpringBootTest(classes = ConstructionAuthConfig.class)
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