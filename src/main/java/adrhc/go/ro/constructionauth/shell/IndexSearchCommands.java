package adrhc.go.ro.constructionauth.shell;

import adrhc.go.ro.constructionauth.datasource.index.UrlContentIndexSearcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;

import static adrhc.go.ro.constructionauth.util.DebugUtils.showMatches;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class IndexSearchCommands {
    private final UrlContentIndexSearcher searcher;

    @ShellMethod(value = "Search the index.")
    public void search(String words) throws IOException {
        showMatches(words, searcher.search(words));
    }
}
