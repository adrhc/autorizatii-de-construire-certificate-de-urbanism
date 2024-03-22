package adrhc.go.ro.constructionauth.datasource.index;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.go.adrhc.persistence.lucene.typedindex.IndexRepository;
import ro.go.adrhc.persistence.lucene.typedindex.TypedIndexContext;

@Component
@Getter
@RequiredArgsConstructor
@Slf4j
public class CloseableIndexRepository {
    private final TypedIndexContext<MontlyPdfIndexRecord> typedIndexContext;
    private final IndexRepository<MonthYear, MontlyPdfIndexRecord> indexRepository;
}
