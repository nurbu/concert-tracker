package com.pluralsight.concerttracker.data;

import com.pluralsight.concerttracker.models.Artist;
import com.pluralsight.concerttracker.models.Concert;
import com.pluralsight.concerttracker.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    List<Concert> findByVenue(Venue venue);

    List<Concert> findByArtist(Artist artist);
}
