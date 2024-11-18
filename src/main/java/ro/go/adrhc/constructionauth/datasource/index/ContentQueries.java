package ro.go.adrhc.constructionauth.datasource.index;

import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.Query;
import org.springframework.stereotype.Component;
import ro.go.adrhc.persistence.lucene.core.bare.token.TokenizationUtils;

import java.io.IOException;
import java.util.List;

import static ro.go.adrhc.constructionauth.datasource.index.UrlContentFieldType.CONTENT_QUERIES;
import static ro.go.adrhc.constructionauth.datasource.index.UrlContentFieldType.content;

@Component
@RequiredArgsConstructor
public class ContentQueries {
	private final TokenizationUtils tokenizationUtils;

	public Query nearTokens(String words) throws IOException {
		List<String> tokens = tokenizeAsList(words);
		if (tokens.size() == 1) {
			return CONTENT_QUERIES.tokenEquals(tokens.getFirst());
		} else {
			return CONTENT_QUERIES.nearTokens(tokens);
		}
	}

	public Query maxFuzziness(String words) throws IOException {
		List<String> tokens = tokenizeAsList(words);
		if (tokens.size() == 1) {
			String token = tokens.getFirst();
			if (token.length() <= 2) {
				return CONTENT_QUERIES.tokenEquals(token);
			} else {
				return CONTENT_QUERIES.maxFuzziness(token);
			}
		} else {
			return CONTENT_QUERIES.maxFuzzinessNearTokens(tokens);
		}
	}

	public Query lowFuzziness(String words) throws IOException {
		List<String> tokens = tokenizeAsList(words);
		if (tokens.size() == 1) {
			String token = tokens.getFirst();
			if (token.length() <= 2) {
				return CONTENT_QUERIES.tokenEquals(token);
			} else {
				return CONTENT_QUERIES.lowFuzziness(token);
			}
		} else {
			return CONTENT_QUERIES.lowFuzzinessNearTokens(tokens);
		}
	}

	private List<String> tokenizeAsList(String words) throws IOException {
		String normalized = tokenizationUtils.normalize(content, words);
		return tokenizationUtils.textToTokenList(normalized);
	}
}
