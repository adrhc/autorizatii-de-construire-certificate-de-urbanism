package ro.go.adrhc.constructionauth.datasource.links;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinksProviderConfig {
	@Bean
	public LinksProvider linksProvider(LinksExtractor linksExtractor,
			@Value("${links-source}") String linksSource) {
		return new LinksProvider(linksSource, linksExtractor);
	}
}
