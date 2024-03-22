package adrhc.go.ro.constructionauth.shell;

import adrhc.go.ro.constructionauth.datasource.index.UrlContentIndexManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class IndexManagementCommands {
    private final UrlContentIndexManager indexManager;

    @ShellMethod(value = "Search the index.")
    public void updateIndex() {
        indexManager.updateIndex();
        log.debug("\nIndex updated!");
    }
}
