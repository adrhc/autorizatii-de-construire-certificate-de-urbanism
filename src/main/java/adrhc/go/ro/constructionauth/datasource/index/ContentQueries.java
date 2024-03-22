package adrhc.go.ro.constructionauth.datasource.index;

import lombok.RequiredArgsConstructor;
import org.apache.lucene.queries.spans.SpanNearQuery;
import org.springframework.stereotype.Component;
import ro.go.adrhc.persistence.lucene.core.token.TokenizationUtils;

import java.io.IOException;
import java.util.List;

import static adrhc.go.ro.constructionauth.datasource.index.UrlContentFieldType.CONTENT_QUERIES;
import static adrhc.go.ro.constructionauth.datasource.index.UrlContentFieldType.content;

@Component
@RequiredArgsConstructor
public class ContentQueries {
    private final TokenizationUtils tokenizationUtils;

    public SpanNearQuery create(String words) throws IOException {
        return CONTENT_QUERIES.closeFuzzyTokens(tokenizeAsList(words));
    }

    private List<String> tokenizeAsList(String words) throws IOException {
        String normalized = tokenizationUtils.normalize(content, words);
        return tokenizationUtils.tokenizeAsList(normalized);
    }
}
