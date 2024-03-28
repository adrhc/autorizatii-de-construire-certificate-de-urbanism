package ro.go.adrhc.module;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.standard.ShellComponent;

@Configuration
@ComponentScan(
        basePackageClasses = SpringBootApplication.class,
        excludeFilters = @ComponentScan.Filter(classes = {
                SpringBootApplication.class, ShellComponent.class
        }))
public class ConstructionAuthConfig {
}
