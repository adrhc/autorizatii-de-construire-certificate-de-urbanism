package ro.go.adrhc.constructionauth.datasource.index;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import ro.go.adrhc.constructionauth.ExcludeShellAutoConfiguration;
import ro.go.adrhc.persistence.lucene.typedindex.IndexRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("very intensive processing")
@SpringBootTest
@ExcludeShellAutoConfiguration
@MockBean(classes = {Shell.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class UrlContentIndexServiceIT {
    @Autowired
    private UrlContentIndexService urlContentIndexService;
    @Autowired
    private IndexRepository<String, UrlContentIndexRecord> indexRepository;

    //    @Test
    void resetIndex() throws IOException {
        indexRepository.reset(Stream.of());
        updateIndex();
    }

    @Test
    void updateIndex() throws IOException {
        urlContentIndexService.updateIndex();
        Optional<UrlContentIndexRecord> indexRecordOptional =
                indexRepository.findById("https://sector5.ro/media/2024/02/LISTA_CERTIFICATE_URBANISM_IAN-2024.pdf");
        assertThat(indexRecordOptional).isNotEmpty();
        UrlContentIndexRecord indexRecord = indexRecordOptional.get();
        log.info("\n{}:\n{}", indexRecord.url(), indexRecord.text());
    }
}