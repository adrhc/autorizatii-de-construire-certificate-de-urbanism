package ro.go.adrhc.constructionauth.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ro.go.adrhc.constructionauth.datasource.index.UrlContentIndexSearcher;
import ro.go.adrhc.constructionauth.datasource.index.UrlContentIndexService;

import java.io.IOException;

import static ro.go.adrhc.constructionauth.util.DebugUtils.showMatches;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class IndexSearchCommands {
    private final UrlContentIndexService indexManager;
    private final UrlContentIndexSearcher searcher;

    @ShellMethod(value = "Search the index.")
    public void search(String words) throws IOException {
        showMatches(words, searcher.search(words));
    }

    @ShellMethod(value = "Update then search the index.")
    public void searchLastUpdate(String words) throws IOException {
        indexManager.updateIndex();
        log.info("\nIndex updated!");
        showMatches(words, searcher.search(words));
    }
}
