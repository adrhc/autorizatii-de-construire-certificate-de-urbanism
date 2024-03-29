package ro.go.adrhc.constructionauth.datasource.index;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ro.go.adrhc.constructionauth.datasource.text.UrlContent;
import ro.go.adrhc.persistence.lucene.typedcore.serde.Identifiable;

public record UrlContentIndexRecord(String url,
        @JsonIgnore String text) implements Identifiable<String> {
    public static UrlContentIndexRecord of(UrlContent urlContent) {
        return new UrlContentIndexRecord(urlContent.url(), urlContent.text());
    }

    @JsonIgnore
    @Override
    public String id() {
        return url;
    }
}
