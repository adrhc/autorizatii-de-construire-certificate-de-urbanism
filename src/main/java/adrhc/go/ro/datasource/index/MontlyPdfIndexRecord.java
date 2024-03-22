package adrhc.go.ro.datasource.index;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ro.go.adrhc.persistence.lucene.typedcore.serde.Identifiable;

public record MontlyPdfIndexRecord(String content, String fileName, MonthYear moment)
        implements Identifiable<MonthYear> {
    @JsonIgnore
    @Override
    public MonthYear id() {
        return moment;
    }
}
