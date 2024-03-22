package adrhc.go.ro.constructionauth.datasource.pages;

import adrhc.go.ro.constructionauth.ExcludeShellAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;

import java.io.IOException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExcludeShellAutoConfiguration
@MockBean(classes = {Shell.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class PageLinksProviderIT {
    private static final String URL = "https://sector5.ro/autorizatii-de-construire-certificate-de-urbanism";
    @Autowired
    private PageLinksProvider linksProvider;

    @Test
    void load() throws IOException {
        Set<String> links = linksProvider.loadLinks(URL);
        assertThat(links).isNotEmpty();
    }
}