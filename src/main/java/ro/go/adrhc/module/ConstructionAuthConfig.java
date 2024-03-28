package ro.go.adrhc.module;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.shell.standard.ShellComponent;
import ro.go.adrhc.constructionauth.config.AppConfiguration;

@Configuration
@ComponentScan(
        basePackageClasses = SpringBootApplication.class,
        excludeFilters = {
                @ComponentScan.Filter(classes = {
                        SpringBootApplication.class, ShellComponent.class
                }),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        classes = AppConfiguration.class)
        })
public class ConstructionAuthConfig {
}
