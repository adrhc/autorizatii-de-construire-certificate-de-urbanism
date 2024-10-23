package ro.go.adrhc.constructionauth.datasource.index;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.go.adrhc.persistence.lucene.FileSystemIndex;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UrlContentIndexSearcher {
	private final FileSystemIndex<String, UrlContentIndexRecord> indexRepository;
	private final ContentQueries contentQueries;

	public List<UrlContentIndexRecord> searchExact(String words) throws IOException {
		return indexRepository.findMany(contentQueries.nearTokens(words));
	}

	public List<UrlContentIndexRecord> searchWithSmallMistakes(String words) throws IOException {
		return indexRepository.findMany(contentQueries.lowFuzziness(words));
	}

	public List<UrlContentIndexRecord> searchWithBigMistakes(String words) throws IOException {
		return indexRepository.findMany(contentQueries.maxFuzziness(words));
	}
}
