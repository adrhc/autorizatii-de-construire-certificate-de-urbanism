package adrhc.go.ro.constructionauth.datasource.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LinksParser {
    @Autowired
    @Value("${link-pattern}")
    private String pattern;

    public Set<String> parseLinks(String page) {
        Set<String> links = new HashSet<>();
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(page);
        while (m.find()) {
            links.add(m.group().replace("\\/", "/"));
        }
        return links;
    }
}
