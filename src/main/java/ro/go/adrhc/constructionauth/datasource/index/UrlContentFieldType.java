package ro.go.adrhc.constructionauth.datasource.index;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import ro.go.adrhc.persistence.lucene.core.bare.field.FieldType;
import ro.go.adrhc.persistence.lucene.core.bare.query.FieldQueries;
import ro.go.adrhc.persistence.lucene.core.typed.field.LuceneFieldSpec;
import ro.go.adrhc.persistence.lucene.core.typed.field.ObjectLuceneFieldMapper;

import static ro.go.adrhc.persistence.lucene.core.bare.field.FieldType.KEYWORD;
import static ro.go.adrhc.persistence.lucene.core.bare.field.FieldType.PHRASE;
import static ro.go.adrhc.persistence.lucene.core.typed.field.ObjectLuceneFieldMapper.stringField;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum UrlContentFieldType implements LuceneFieldSpec<UrlContentIndexRecord> {
	url(KEYWORD, stringField(UrlContentIndexRecord::getId), true),
	content(PHRASE, stringField(UrlContentIndexRecord::text));

	public static final FieldQueries CONTENT_QUERIES = FieldQueries.create(UrlContentFieldType.content);

	private final FieldType fieldType;
	private final ObjectLuceneFieldMapper<UrlContentIndexRecord, ?> fieldSerde;
	private final boolean isIdField;

	UrlContentFieldType(FieldType fieldType, ObjectLuceneFieldMapper<UrlContentIndexRecord, ?> fieldSerde) {
		this.fieldType = fieldType;
		this.fieldSerde = fieldSerde;
		this.isIdField = false;
	}
}
