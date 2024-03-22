package adrhc.go.ro.constructionauth;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 */
@EnableFeignClients
@SpringBootApplication
public class ConstructionAuthorizationApp {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
