package ro.go.adrhc.constructionauth.datasource.index;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.go.adrhc.persistence.lucene.FileSystemIndex;
import ro.go.adrhc.persistence.lucene.FileSystemIndexImpl;
import ro.go.adrhc.persistence.lucene.core.bare.token.TokenizationUtils;
import ro.go.adrhc.persistence.lucene.operations.params.IndexServicesParamsFactory;
import ro.go.adrhc.persistence.lucene.operations.params.IndexServicesParamsFactoryBuilder;

import static ro.go.adrhc.persistence.lucene.core.bare.analysis.AnalyzerFactory.defaultAnalyzer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class IndexConfig {
	private final IndexProperties indexProperties;

	@Bean
	public TokenizationUtils tokenizationUtils() {
		return new TokenizationUtils(defaultAnalyzer().orElseThrow());
	}

	@Bean
	public FileSystemIndex<String, UrlContentIndexRecord> indexRepository() {
		log.info("\nopening index: {}", indexProperties.getPath());
		return FileSystemIndexImpl.of(indexServicesParamsFactory());
	}

	@Bean
	public IndexServicesParamsFactory<UrlContentIndexRecord> indexServicesParamsFactory() {
		log.info("\nopening the index: {}", indexProperties.getPath());
		return IndexServicesParamsFactoryBuilder.of(
						UrlContentIndexRecord.class, UrlContentFieldType.class,
						indexProperties.getPath())
				.build().orElseThrow();
	}
}
