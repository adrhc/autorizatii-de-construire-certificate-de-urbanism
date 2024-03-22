package adrhc.go.ro.constructionauth.managers;

import adrhc.go.ro.constructionauth.ExcludeShellAutoConfiguration;
import adrhc.go.ro.constructionauth.datasource.index.UrlContentIndexRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.queries.spans.SpanNearQuery;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import ro.go.adrhc.persistence.lucene.core.token.TokenizationUtils;
import ro.go.adrhc.persistence.lucene.typedindex.IndexRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static adrhc.go.ro.constructionauth.datasource.index.UrlContentFieldType.CONTENT_QUERIES;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled("very intensive processing")
@SpringBootTest
@ExcludeShellAutoConfiguration
@MockBean(classes = {Shell.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class UrlContentIndexManagerIT {
    @Autowired
    private UrlContentIndexManager urlContentIndexManager;
    @Autowired
    private IndexRepository<String, UrlContentIndexRecord> indexRepository;
    @Autowired
    private TokenizationUtils tokenizationUtils;

    @Test
    void indexQuery() throws IOException {
        // original text
        List<UrlContentIndexRecord> matches = indexRepository
                .findAllMatches(createContentQuery("TUDOR MARIAN ȘI TUDOR MIHAELA RUXANDRA"));
        assertThat(matches).isNotEmpty();
        // "MARIAN" wrongly spelled as "MARAN"
        matches = indexRepository.findAllMatches(createContentQuery("TUDOR MARAN ȘI TUDOR MIHAELA RUXANDRA"));
        assertThat(matches).isNotEmpty();
        // "MARIAN" wrongly spelled as "MARXIAN"
        matches = indexRepository.findAllMatches(createContentQuery("TUDOR MARXIAN ȘI TUDOR MIHAELA RUXANDRA"));
        assertThat(matches).isNotEmpty();
        // "MARIAN" wrongly spelled as "MARXAN"
        matches = indexRepository.findAllMatches(createContentQuery("TUDOR MARXAN ȘI TUDOR MIHAELA RUXANDRA"));
        assertThat(matches).isNotEmpty();
        // "MARIAN" wrongly spelled as "MARAIN"
        matches = indexRepository.findAllMatches(createContentQuery("TUDOR MARAIN ȘI TUDOR MIHAELA RUXANDRA"));
        assertThat(matches).isNotEmpty();
        // "MARIAN" wrongly spelled as "MARI"
        matches = indexRepository.findAllMatches(createContentQuery("TUDOR MARI ȘI TUDOR MIHAELA RUXANDRA"));
        assertThat(matches).isNotEmpty();
        // "MARIAN" wrongly spelled as "ARAI"
        matches = indexRepository.findAllMatches(createContentQuery("TUDOR ARAI ȘI TUDOR MIHAELA RUXANDRA"));
        assertThat(matches).isEmpty();
    }

    private SpanNearQuery createContentQuery(String words) throws IOException {
        return CONTENT_QUERIES.closeFuzzyTokens(tokenizeAsList(words));
    }

    private List<String> tokenizeAsList(String words) throws IOException {
        String normalized = tokenizationUtils.normalize("", words);
        return tokenizationUtils.tokenizeAsList(normalized);
    }

    @Test
    void resetIndex() throws IOException {
        indexRepository.reset(Stream.of());
        updateIndex();
    }

    @Test
    void updateIndex() throws IOException {
        urlContentIndexManager.updateIndex();
        Optional<UrlContentIndexRecord> indexRecordOptional =
                indexRepository.findById("https://sector5.ro/media/2024/02/LISTA_CERTIFICATE_URBANISM_IAN-2024.pdf");
        assertThat(indexRecordOptional).isNotEmpty();
        UrlContentIndexRecord indexRecord = indexRecordOptional.get();
        log.info("\n{}:\n{}", indexRecord.url(), indexRecord.text());
    }
}