package com.campus.lostfound.config;

import com.campus.lostfound.model.Item;
import com.campus.lostfound.repo.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import java.util.Set;

/** Java-based, XML-free configuration (Unit 3). */
@Configuration
public class AppConfig implements RepositoryRestConfigurer {

    @Bean
    public StopWords stopWords() {
        return new StopWords(Set.of("the", "and", "with", "for", "was", "has", "near", "from",
                "this", "that", "have", "are", "ago", "found", "lost", "item", "left", "last", "some"));
    }

    @Override  // expose "id" in JSON and allow the React dev server
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Item.class);
        cors.addMapping("/**").allowedOrigins("http://localhost:5173").allowedMethods("*");
    }

    @Bean  // seed demo data at startup
    CommandLineRunner seed(ItemRepository repo) {
        return args -> {
            repo.save(make("Black Dell laptop bag", "Black backpack with Dell laptop and charger inside", "Bags", "Library", "LOST", "ravi@college.edu"));
            repo.save(make("Backpack found", "Black bag with laptop charger and notebooks found on bench", "Bags", "Library 2nd floor", "FOUND", "security@college.edu"));
            repo.save(make("Blue water bottle", "Blue steel bottle with sticker", "Accessories", "Canteen", "FOUND", "security@college.edu"));
            repo.save(make("Student ID card", "ID card of CSE 3rd year student named Anil", "Documents", "Block A", "LOST", "anil@college.edu"));
        };
    }

    private Item make(String t, String d, String c, String l, String type, String contact) {
        Item i = new Item();
        i.setTitle(t); i.setDescription(d); i.setCategory(c);
        i.setLocation(l); i.setType(type); i.setContact(contact);
        return i;
    }
}
