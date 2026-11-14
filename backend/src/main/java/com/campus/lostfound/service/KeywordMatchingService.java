package com.campus.lostfound.service;

import com.campus.lostfound.config.StopWords;
import com.campus.lostfound.model.Item;
import com.campus.lostfound.repo.ItemRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * "AI" matcher: NLP text similarity (tokenise, remove stop words, Jaccard similarity).
 * Compares a LOST item with open FOUND items (and vice versa).
 */
@Service
public class KeywordMatchingService implements MatchingService {
    private static final Logger log = LoggerFactory.getLogger(KeywordMatchingService.class);
    private final ItemRepository repo;
    private final StopWords stopWords;
    private final int threshold;

    // Constructor injection (Unit 2/3)
    public KeywordMatchingService(ItemRepository repo, StopWords stopWords,
                                  @Value("${app.match.threshold}") int threshold) {
        this.repo = repo;
        this.stopWords = stopWords;
        this.threshold = threshold;
    }

    @PostConstruct void init()    { log.info("MatchingService ready, threshold={}%", threshold); }
    @PreDestroy  void shutdown()  { log.info("MatchingService shutting down"); }

    @Override
    public List<Match> findMatches(Item item) {
        String opposite = "LOST".equals(item.getType()) ? "FOUND" : "LOST";
        Set<String> base = tokens(item);
        return repo.findByTypeAndStatus(opposite, "OPEN").stream()
                .map(c -> new Match(c, score(base, tokens(c))))
                .filter(m -> m.score() >= threshold)
                .sorted(Comparator.comparingInt(Match::score).reversed())
                .toList();
    }

    private Set<String> tokens(Item i) {
        String text = (i.getTitle() + " " + i.getDescription() + " " + i.getCategory()).toLowerCase();
        return Arrays.stream(text.split("[^a-z0-9]+"))
                .filter(w -> w.length() > 2 && !stopWords.words().contains(w))
                .collect(Collectors.toSet());
    }

    private int score(Set<String> a, Set<String> b) {
        if (a.isEmpty() || b.isEmpty()) return 0;
        Set<String> inter = new HashSet<>(a); inter.retainAll(b);
        Set<String> union = new HashSet<>(a); union.addAll(b);
        return (int) Math.round(100.0 * inter.size() / union.size());
    }
}
