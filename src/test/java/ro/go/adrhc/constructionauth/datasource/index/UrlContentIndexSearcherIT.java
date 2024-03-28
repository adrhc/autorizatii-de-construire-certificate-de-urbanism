package ro.go.adrhc.constructionauth.datasource.index;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import ro.go.adrhc.constructionauth.ConstructionAuthConfig;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ro.go.adrhc.constructionauth.util.DebugUtils.showMatches;

@Disabled("requires the index to be present")
@EnableConfigurationProperties
@SpringBootTest(classes = ConstructionAuthConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class UrlContentIndexSearcherIT {
    @Autowired
    private UrlContentIndexService urlContentIndexService;
    @Autowired
    private UrlContentIndexSearcher searcher;

    @ParameterizedTest
    @ValueSource(strings = {"Gheorghieni", "Gheorhieni", "GheorgXhieni", "GheorXhieni", "Ghoerghieni",
            "heorghien", "GheorghieXX", "Gheorghieni 15-17", "Gheorghieni 27-29-29A-31-33",
            "Gheorghieni 19", "Gheorghieni 20", "Gheorghieni 21", "Gheorghieni 22", "Gheorghieni 23",
            "Gheorghieni 24", "Gheorghieni 26", "Gheorghieni 27", "Gheorghieni 28", "Gheorghieni 29"})
    void gheorghieniMatches(String words) throws IOException {
        urlContentIndexService.updateIndex();

        List<UrlContentIndexRecord> matches = searcher.search(words);
        showMatches(words, matches);
        assertThat(matches).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Gheorghieni 19-25", "Gheorghieni 30",
            "Gheorghieni 31", "Gheorghieni 32", "Gheorghieni 33"})
    void gheorghieniMissed(String words) throws IOException {
        urlContentIndexService.updateIndex();

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
        urlContentIndexService.updateIndex();

        List<UrlContentIndexRecord> matches = searcher.search(words);
        showMatches(words, matches);
        assertThat(matches).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"TUDOR MARXXX ȘI TUDOR MIHAELA RUXANDRA",
            "TUDOR ARAI ȘI TUDOR MIHAELA RUXANDRA"})
    void notMatching(String words) throws IOException {
        urlContentIndexService.updateIndex();

        List<UrlContentIndexRecord> matches = searcher.search(words);
        assertThat(matches).isEmpty();
    }
}