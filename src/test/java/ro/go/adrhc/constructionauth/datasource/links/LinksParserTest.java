package ro.go.adrhc.constructionauth.datasource.links;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@EnableConfigurationProperties
@SpringBootTest(classes = {LinksParser.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class LinksParserTest {
	@Value("${:classpath:sector5-start.html}")
	private Resource sector5StartPage;

	@Autowired
	private LinksParser parser;

	@Test
	void parseLinks() throws IOException {
		String page = sector5StartPage.getContentAsString(StandardCharsets.UTF_8);
		Set<String> links = parser.parseLinks(page);
		assertThat(links).isNotEmpty();
	}
}