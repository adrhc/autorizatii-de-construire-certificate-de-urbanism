package ro.go.adrhc.constructionauth.datasource.index;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.go.adrhc.persistence.lucene.core.token.TokenizationUtils;
import ro.go.adrhc.persistence.lucene.typedindex.IndexRepository;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class IndexConfig {
    private final CloseableIndexRepositoryFactory closeableIndexRepositoryFactory;

    @Bean
    public IndexRepository<String, UrlContentIndexRecord>
    indexRepository() throws IOException {
        return closeableIndexRepository().getIndexRepository();
    }

    @Bean
    public TokenizationUtils tokenizationUtils() throws IOException {
        return new TokenizationUtils(
                closeableIndexRepository().getTypedIndexContext().getAnalyzer());
    }

    @Bean
    public CloseableIndexRepository closeableIndexRepository() throws IOException {
        return closeableIndexRepositoryFactory.create();
    }
}
