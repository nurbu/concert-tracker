package com.pluralsight.concerttracker.data;

import com.pluralsight.concerttracker.models.Artist;
import com.pluralsight.concerttracker.models.Concert;
import com.pluralsight.concerttracker.models.Promoter;
import com.pluralsight.concerttracker.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    List<Concert> findByVenue(Venue venue);

    List<Concert> findByArtist(Artist artist);

    List<Concert> findByPromoter(Promoter promoter);

    List<Concert> findByConcertYear(int year);

    List<Concert> findByArtist_NameContaining(String artistName);

    List<Concert> findByVenue_NameContaining(String venueName);

    List<Concert> findByTicketPriceLessThanEqual(double ticketPrice);

    List<Concert> findConcertByPriceRange(double minPrice, double maxPrice);
}
