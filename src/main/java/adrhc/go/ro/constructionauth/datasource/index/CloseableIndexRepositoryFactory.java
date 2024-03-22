package adrhc.go.ro.constructionauth.datasource.index;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.go.adrhc.persistence.lucene.typedindex.IndexRepositoryFactory;
import ro.go.adrhc.persistence.lucene.typedindex.TypedIndexContext;
import ro.go.adrhc.persistence.lucene.typedindex.factories.TypedIndexFactoriesParamsFactory;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CloseableIndexRepositoryFactory {
    private final IndexProperties indexProperties;

    private static TypedIndexContext<MontlyPdfIndexRecord>
    createTypedIndexContext(IndexProperties indexProperties) throws IOException {
        return TypedIndexFactoriesParamsFactory.create(
                MontlyPdfIndexRecord.class, MontlyPdfFieldType.class,
                indexProperties.getPath());
    }

    public CloseableIndexRepository create() throws IOException {
        TypedIndexContext<MontlyPdfIndexRecord>
                typedIndexContext = createTypedIndexContext(indexProperties);
        return new CloseableIndexRepository(typedIndexContext,
                IndexRepositoryFactory.create(typedIndexContext));
    }
}
