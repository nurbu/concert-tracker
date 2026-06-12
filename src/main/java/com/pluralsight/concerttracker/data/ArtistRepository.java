package com.pluralsight.concerttracker.data;

import com.pluralsight.concerttracker.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

}
