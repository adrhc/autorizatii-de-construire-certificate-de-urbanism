package adrhc.go.ro.constructionauth.datasource.index;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.go.adrhc.persistence.lucene.typedindex.IndexRepository;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UrlContentIndexSearcher {
    private final IndexRepository<String, UrlContentIndexRecord> indexRepository;
    private final ContentQueries contentQueries;

    public List<UrlContentIndexRecord> search(String words) throws IOException {
        return indexRepository.findAllMatches(contentQueries.create(words));
    }
}