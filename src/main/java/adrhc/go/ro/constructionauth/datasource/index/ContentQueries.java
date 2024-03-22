package adrhc.go.ro.constructionauth.datasource.index;

import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.Query;
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

    public Query create(String words) throws IOException {
        List<String> tokens = tokenizeAsList(words);
        if (tokens.size() == 1) {
            return CONTENT_QUERIES.fuzzy(tokens.get(0));
        } else {
            return CONTENT_QUERIES.closeFuzzyTokens(tokens);
        }
    }

    private List<String> tokenizeAsList(String words) throws IOException {
        String normalized = tokenizationUtils.normalize(content, words);
        return tokenizationUtils.tokenizeAsList(normalized);
    }
}
