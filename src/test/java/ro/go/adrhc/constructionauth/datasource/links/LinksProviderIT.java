package ro.go.adrhc.constructionauth.datasource.links;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import ro.go.adrhc.constructionauth.ConstructionAuthConfig;

import java.io.IOException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@EnableConfigurationProperties
@SpringBootTest(classes = ConstructionAuthConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class LinksProviderIT {
	@Autowired
	private LinksProvider linksProvider;

	@Test
	void loadLinks() throws IOException {
		Set<String> links = linksProvider.loadLinks();
		assertThat(links).isNotEmpty();
	}
}