package adrhc.go.ro.constructionauth.datasource.index;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import ro.go.adrhc.persistence.lucene.core.field.FieldType;
import ro.go.adrhc.persistence.lucene.typedcore.field.TypedField;
import ro.go.adrhc.persistence.lucene.typedcore.field.TypedFieldSerde;

import static ro.go.adrhc.persistence.lucene.core.field.FieldType.KEYWORD;
import static ro.go.adrhc.persistence.lucene.core.field.FieldType.PHRASE;
import static ro.go.adrhc.persistence.lucene.typedcore.field.TypedFieldSerde.stringField;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum MontlyPdfFieldType implements TypedField<MontlyPdfIndexRecord> {
    moment(KEYWORD, stringField(MontlyPdfIndexRecord::getId, it -> MonthYear.parse((String) it)), true),
    content(PHRASE, stringField(MontlyPdfIndexRecord::content)),
    fileName(KEYWORD, stringField(MontlyPdfIndexRecord::fileName));

    private final FieldType fieldType;
    private final TypedFieldSerde<MontlyPdfIndexRecord> fieldSerde;
    private final boolean isIdField;

    MontlyPdfFieldType(FieldType fieldType, TypedFieldSerde<MontlyPdfIndexRecord> fieldSerde) {
        this.fieldType = fieldType;
        this.fieldSerde = fieldSerde;
        this.isIdField = false;
    }
}
