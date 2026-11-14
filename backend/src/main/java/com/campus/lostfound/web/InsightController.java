package com.campus.lostfound.web;

import com.campus.lostfound.model.Item;
import com.campus.lostfound.repo.ItemRepository;
import com.campus.lostfound.service.Match;
import com.campus.lostfound.service.MatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/** Spring MVC REST controller for custom endpoints (CRUD itself is handled by Spring Data REST). */
@RestController
@RequestMapping("/api")
public class InsightController {
    private final ItemRepository repo;
    private final MatchingService matcher;

    public InsightController(ItemRepository repo, MatchingService matcher) {
        this.repo = repo;
        this.matcher = matcher;
    }

    @GetMapping("/items/{id}/matches")              // @PathVariable
    public ResponseEntity<List<Match>> matches(@PathVariable Long id) {
        return repo.findById(id)
                .map(i -> ResponseEntity.ok(matcher.findMatches(i)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")                          // @RequestParam
    public List<Item> search(@RequestParam String q) {
        return repo.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(q, q);
    }

    @GetMapping("/stats")                           // counter, like Lab 1
    public Map<String, Long> stats() {
        return Map.of(
                "lostOpen", repo.countByTypeAndStatus("LOST", "OPEN"),
                "foundOpen", repo.countByTypeAndStatus("FOUND", "OPEN"),
                "lostResolved", repo.countByTypeAndStatus("LOST", "RESOLVED"),
                "foundResolved", repo.countByTypeAndStatus("FOUND", "RESOLVED"));
    }
}
