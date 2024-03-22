package adrhc.go.ro.constructionauth.datasource.index;

import adrhc.go.ro.constructionauth.ExcludeShellAutoConfiguration;
import adrhc.go.ro.constructionauth.managers.UrlContentIndexManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ro.go.adrhc.util.text.StringUtils.concat;

@Disabled("requires the index to be present")
@SpringBootTest
@ExcludeShellAutoConfiguration
@MockBean(classes = {Shell.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class UrlContentIndexSearcherIT {
    @Autowired
    private UrlContentIndexManager urlContentIndexManager;
    @Autowired
    private UrlContentIndexSearcher searcher;

    private static void showMatches(String words, Collection<UrlContentIndexRecord> matches) {
        String concatenatedMatches = concat(UrlContentIndexRecord::url, matches);
        if (concatenatedMatches.isBlank()) {
            log.info("\nMatches for \"{}\": none", words);
        } else {
            log.info("\nMatches for \"{}\":\n{}", words, concatenatedMatches);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Gheorghieni", "Gheorhieni", "GheorgXhieni", "GheorXhieni", "Ghoerghieni",
            "heorghien", "GheorghieXX", "Gheorghieni 15-17", "Gheorghieni 27-29-29A-31-33",
            "Gheorghieni 19", "Gheorghieni 20", "Gheorghieni 21", "Gheorghieni 22", "Gheorghieni 23",
            "Gheorghieni 24", "Gheorghieni 26", "Gheorghieni 27", "Gheorghieni 28", "Gheorghieni 29"})
    void gheorghieniMatches(String words) throws IOException {
        urlContentIndexManager.updateIndex();

        List<UrlContentIndexRecord> matches = searcher.search(words);
        showMatches(words, matches);
        assertThat(matches).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Gheorghieni 19-25", "Gheorghieni 30",
            "Gheorghieni 31", "Gheorghieni 32", "Gheorghieni 33"})
    void gheorghieniMissed(String words) throws IOException {
        urlContentIndexManager.updateIndex();

        List<UrlContentIndexRecord> matches = searcher.search(words);
        showMatches(words, matches);
        assertThat(matches).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"TUDOR MARIAN ȘI TUDOR MIHAELA RUXANDRA",
            "TUDOR MARAN ȘI TUDOR MIHAELA RUXANDRA", "TUDOR MARXIAN ȘI TUDOR MIHAELA RUXANDRA",
            "TUDOR MARXAN ȘI TUDOR MIHAELA RUXANDRA", "TUDOR MARAIN ȘI TUDOR MIHAELA RUXANDRA",
            "TUDOR MARI ȘI TUDOR MIHAELA RUXANDRA", "TUDOR MARIXX ȘI TUDOR MIHAELA RUXANDRA"})
    void matching(String words) throws IOException {
        urlContentIndexManager.updateIndex();

        List<UrlContentIndexRecord> matches = searcher.search(words);
        showMatches(words, matches);
        assertThat(matches).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"TUDOR MARXXX ȘI TUDOR MIHAELA RUXANDRA",
            "TUDOR ARAI ȘI TUDOR MIHAELA RUXANDRA"})
    void notMatching(String words) throws IOException {
        urlContentIndexManager.updateIndex();

        List<UrlContentIndexRecord> matches = searcher.search(words);
        assertThat(matches).isEmpty();
    }
}