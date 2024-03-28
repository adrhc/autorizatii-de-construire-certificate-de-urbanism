package ro.go.adrhc.constructionauth.datasource.index;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "index")
@Component
@Getter
@Setter
@Slf4j
@ToString
public class IndexProperties {
    private Path path;
}
