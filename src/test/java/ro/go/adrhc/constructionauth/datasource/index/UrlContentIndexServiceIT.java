package ro.go.adrhc.constructionauth.datasource.index;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import ro.go.adrhc.constructionauth.ConstructionAuthConfig;
import ro.go.adrhc.persistence.lucene.FileSystemIndex;
import ro.go.adrhc.util.text.StringUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnableConfigurationProperties
@ContextConfiguration(initializers = UrlContentIndexServiceIT.IndexPathInitializer.class)
@SpringBootTest(classes = ConstructionAuthConfig.class)
@DirtiesContext
@Slf4j
class UrlContentIndexServiceIT {
	@TempDir
	static Path tmpDir;
	@Autowired
	private UrlContentIndexService urlContentIndexService;
	@Autowired
	private FileSystemIndex<String, UrlContentIndexRecord> indexRepository;

	@Test
	void resetIndex() throws IOException {
		indexRepository.reset(Stream.of());
		updateIndex();
	}

	@Disabled("for debug only")
	@Test
	void updateIndex() throws IOException {
		urlContentIndexService.updateIndex();
		List<String> urls = indexRepository.getAllIds();
		log.info("\nurls:{}", StringUtils.concat("\n", urls));
		assertThat(urls).isNotEmpty();
		Optional<UrlContentIndexRecord> indexRecordOptional = indexRepository.findById(urls.getFirst());
		assertThat(indexRecordOptional).isNotEmpty();
	}

	static class IndexPathInitializer
			implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(@NonNull ConfigurableApplicationContext context) {
			TestPropertyValues.of(STR."index.path=\{tmpDir}").applyTo(context);
		}
	}
}