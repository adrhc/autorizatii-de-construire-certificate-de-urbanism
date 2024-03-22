package adrhc.go.ro.constructionauth.datasource.index;

import adrhc.go.ro.constructionauth.datasource.text.UrlContentProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.go.adrhc.persistence.lucene.typedindex.IndexRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlContentIndexManager {
    private final IndexRepository<String, UrlContentIndexRecord> indexRepository;
    private final UrlContentProvider urlContentProvider;

    public void updateIndex() {
        urlContentProvider.load(this::filterLinks)
                .map(UrlContentIndexRecord::of).forEach(this::insert);
    }

    private void insert(UrlContentIndexRecord urlContentIndexRecord) {
        try {
            indexRepository.upsert(urlContentIndexRecord);
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        }
    }

    private boolean filterLinks(String url) {
        try {
            return indexRepository.findById(url).isEmpty();
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        }
        return false;
    }
}
