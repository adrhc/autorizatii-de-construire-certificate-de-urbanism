package adrhc.go.ro.constructionauth.shell;

import adrhc.go.ro.constructionauth.datasource.index.UrlContentIndexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class IndexManagementCommands {
    private final UrlContentIndexService indexManager;

    @ShellMethod(value = "Update the index.", key = {"update", "update-index"})
    public void updateIndex() {
        indexManager.updateIndex();
        log.info("\nIndex updated!");
    }
}
