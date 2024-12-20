package ro.go.adrhc.constructionauth.datasource.links;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import ro.go.adrhc.constructionauth.lib.URLContentReader;

import java.io.IOException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@EnableConfigurationProperties
@SpringBootTest(classes = {URLContentReader.class, LinksParser.class, LinksExtractor.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class LinksExtractorIT {
	@Value("${links-source}")
	private String linksSource;
	@Autowired
	private LinksExtractor linksExtractor;

	@Test
	void load() throws IOException {
		Set<String> links = linksExtractor.loadLinks(linksSource);
		assertThat(links).isNotEmpty();
	}
}