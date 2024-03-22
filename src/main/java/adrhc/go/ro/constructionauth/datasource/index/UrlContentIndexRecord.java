package adrhc.go.ro.constructionauth.datasource.index;

import adrhc.go.ro.constructionauth.datasource.text.UrlContent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ro.go.adrhc.persistence.lucene.typedcore.serde.Identifiable;

public record UrlContentIndexRecord(String url, String text) implements Identifiable<String> {
    public static UrlContentIndexRecord of(UrlContent urlContent) {
        return new UrlContentIndexRecord(urlContent.url(), urlContent.text());
    }

    @JsonIgnore
    @Override
    public String id() {
        return url;
    }
}
