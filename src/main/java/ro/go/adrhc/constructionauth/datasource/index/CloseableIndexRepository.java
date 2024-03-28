package ro.go.adrhc.constructionauth.datasource.index;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.persistence.lucene.typedindex.IndexRepository;
import ro.go.adrhc.persistence.lucene.typedindex.TypedIndexContext;

import java.io.Closeable;
import java.io.IOException;

@Getter
@RequiredArgsConstructor
@Slf4j
public class CloseableIndexRepository implements Closeable {
    private final TypedIndexContext<UrlContentIndexRecord> typedIndexContext;
    private final IndexRepository<String, UrlContentIndexRecord> indexRepository;

    @Override
    public void close() throws IOException {
        typedIndexContext.close();
    }
}
