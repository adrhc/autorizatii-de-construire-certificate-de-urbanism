package ro.go.adrhc.constructionauth.util;

import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.constructionauth.datasource.index.UrlContentIndexRecord;

import java.util.Collection;

import static ro.go.adrhc.util.text.StringUtils.concat;

@Slf4j
public class DebugUtils {
	public static void showMatches(String words, Collection<UrlContentIndexRecord> matches) {
		String concatenatedMatches = concat(UrlContentIndexRecord::url, matches);
		if (concatenatedMatches.isBlank()) {
			log.info("\nMatches for \"{}\": none", words);
		} else {
			log.info("\nMatches for \"{}\":\n{}", words, concatenatedMatches);
		}
	}
}
