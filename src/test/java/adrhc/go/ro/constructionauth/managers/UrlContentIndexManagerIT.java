package adrhc.go.ro.constructionauth.managers;

import adrhc.go.ro.constructionauth.ExcludeShellAutoConfiguration;
import adrhc.go.ro.constructionauth.datasource.index.ContentQueries;
import adrhc.go.ro.constructionauth.datasource.index.UrlContentIndexRecord;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import ro.go.adrhc.persistence.lucene.typedindex.IndexRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ro.go.adrhc.util.text.StringUtils.concat;

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
    private ContentQueries contentQueries;

    private static void showMatches(String words, Collection<UrlContentIndexRecord> matches) {
        log.info("\nMatches for \"{}\":\n{}", words, concat(UrlContentIndexRecord::url, matches));
    }

    @Test
    void gheorghieniQuery() throws IOException {
        List<UrlContentIndexRecord> matches = findAllMatchesByContent("Gheorghieni");
        showMatches("Gheorghieni", matches);
        assertThat(matches).isNotEmpty();

        matches = findAllMatchesByContent("Gheorghieni 15-17");
        showMatches("Gheorghieni 15-17", matches);
        assertThat(matches).isNotEmpty();

        matches = findAllMatchesByContent("Gheorghieni 27-29-29A-31-33");
        showMatches("Gheorghieni 27-29-29A-31-33", matches);
        assertThat(matches).isNotEmpty();

        matches = findAllMatchesByContent("Gheorghieni 19-25");
        showMatches("Gheorghieni 19-25", matches);
        assertThat(matches).isEmpty();
    }

    @Test
    void indexQuery() throws IOException {
        // original text
        List<UrlContentIndexRecord> matches = findAllMatchesByContent("TUDOR MARIAN ȘI TUDOR MIHAELA RUXANDRA");
        assertThat(matches).isNotEmpty();
        // "MARIAN" wrongly spelled as "MARAN"
        matches = findAllMatchesByContent("TUDOR MARAN ȘI TUDOR MIHAELA RUXANDRA");
        assertThat(matches).isNotEmpty();
        // "MARIAN" wrongly spelled as "MARXIAN"
        matches = findAllMatchesByContent("TUDOR MARXIAN ȘI TUDOR MIHAELA RUXANDRA");
        assertThat(matches).isNotEmpty();
        // "MARIAN" wrongly spelled as "MARXAN"
        matches = findAllMatchesByContent("TUDOR MARXAN ȘI TUDOR MIHAELA RUXANDRA");
        assertThat(matches).isNotEmpty();
        // "MARIAN" wrongly spelled as "MARAIN"
        matches = findAllMatchesByContent("TUDOR MARAIN ȘI TUDOR MIHAELA RUXANDRA");
        assertThat(matches).isNotEmpty();
        // "MARIAN" wrongly spelled as "MARI"
        matches = findAllMatchesByContent("TUDOR MARI ȘI TUDOR MIHAELA RUXANDRA");
        assertThat(matches).isNotEmpty();
        // "MARIAN" wrongly spelled as "MARIXX"
        matches = findAllMatchesByContent("TUDOR MARIXX ȘI TUDOR MIHAELA RUXANDRA");
        assertThat(matches).isNotEmpty();
        // "MARIAN" wrongly spelled as "MARXXX"
        matches = findAllMatchesByContent("TUDOR MARXXX ȘI TUDOR MIHAELA RUXANDRA");
        assertThat(matches).isEmpty();
        // "MARIAN" wrongly spelled as "ARAI"
        matches = findAllMatchesByContent("TUDOR ARAI ȘI TUDOR MIHAELA RUXANDRA");
        assertThat(matches).isEmpty();
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

    private List<UrlContentIndexRecord> findAllMatchesByContent(String words) throws IOException {
        return indexRepository.findAllMatches(contentQueries.create(words));
    }
}