package adrhc.go.ro.constructionauth.datasource.index;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.go.adrhc.persistence.lucene.typedindex.IndexRepository;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class IndexConfig {
    private final CloseableIndexRepositoryFactory closeableIndexRepositoryFactory;

    @Bean
    public IndexRepository<MonthYear, MontlyPdfIndexRecord>
    indexRepository() throws IOException {
        return closeableIndexRepository().getIndexRepository();
    }

    @Bean
    public CloseableIndexRepository closeableIndexRepository() throws IOException {
        return closeableIndexRepositoryFactory.create();
    }
}
