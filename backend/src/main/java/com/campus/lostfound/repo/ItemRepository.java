package com.campus.lostfound.repo;

import com.campus.lostfound.model.Item;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/** Spring Data REST auto-exposes CRUD at /data/items plus the query methods at /data/items/search/... */
@RepositoryRestResource(path = "items", collectionResourceRel = "items")
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByType(@Param("type") String type);
    List<Item> findByStatus(@Param("status") String status);
    List<Item> findByTypeAndStatus(@Param("type") String type, @Param("status") String status);
    List<Item> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String t, String d);
    long countByTypeAndStatus(String type, String status);
}
