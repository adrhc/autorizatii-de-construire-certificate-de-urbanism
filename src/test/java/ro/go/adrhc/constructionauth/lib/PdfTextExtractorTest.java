package ro.go.adrhc.constructionauth.lib;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import ro.go.adrhc.constructionauth.ConstructionAuthConfig;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@EnableConfigurationProperties
@SpringBootTest(classes = ConstructionAuthConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class PdfTextExtractorTest {
    @Value("${:classpath:LISTA_CERTIFICATE_URBANISM_IAN-2024.pdf}")
    private Resource pdf;
    @Autowired
    private PdfTextExtractor pdfTextExtractor;

    @Test
    void extractText() throws IOException {
        Optional<String> text = pdfTextExtractor.extractText(pdf.getFile());
        assertThat(text).isNotEmpty();
        log.info("\n{}", text);
    }
}