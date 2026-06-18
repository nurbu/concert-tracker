package com.pluralsight.concerttracker.data;

import com.pluralsight.concerttracker.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Derived queries for Artist model
 */
import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    List<Artist> findByGenre(String genre);

    List<Artist> findByNameContainingIgnoreCase(String name);


}
