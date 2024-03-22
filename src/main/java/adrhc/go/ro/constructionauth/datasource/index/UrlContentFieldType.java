package adrhc.go.ro.constructionauth.datasource.index;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import ro.go.adrhc.persistence.lucene.core.field.FieldType;
import ro.go.adrhc.persistence.lucene.core.query.FieldQueries;
import ro.go.adrhc.persistence.lucene.typedcore.field.TypedField;
import ro.go.adrhc.persistence.lucene.typedcore.field.TypedFieldSerde;

import static ro.go.adrhc.persistence.lucene.core.field.FieldType.KEYWORD;
import static ro.go.adrhc.persistence.lucene.core.field.FieldType.PHRASE;
import static ro.go.adrhc.persistence.lucene.typedcore.field.TypedFieldSerde.stringField;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum UrlContentFieldType implements TypedField<UrlContentIndexRecord> {
    url(KEYWORD, stringField(UrlContentIndexRecord::getId), true),
    content(PHRASE, stringField(UrlContentIndexRecord::text));

    public static final FieldQueries CONTENT_QUERIES = FieldQueries.create(UrlContentFieldType.content);

    private final FieldType fieldType;
    private final TypedFieldSerde<UrlContentIndexRecord> fieldSerde;
    private final boolean isIdField;

    UrlContentFieldType(FieldType fieldType, TypedFieldSerde<UrlContentIndexRecord> fieldSerde) {
        this.fieldType = fieldType;
        this.fieldSerde = fieldSerde;
        this.isIdField = false;
    }
}
