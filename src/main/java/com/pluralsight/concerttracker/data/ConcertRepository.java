package com.pluralsight.concerttracker.data;

import com.pluralsight.concerttracker.models.Artist;
import com.pluralsight.concerttracker.models.Concert;
import com.pluralsight.concerttracker.models.Promoter;
import com.pluralsight.concerttracker.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    List<Concert> findByVenue(Venue venue);

    List<Concert> findByArtist(Artist artist);

    List<Concert> findByPromoter(Promoter promoter);

    List<Concert> findByConcertYear(int year);

    List<Concert> findByArtist_NameContaining(String artistName);

    List<Concert> findByVenue_NameContaining(String venueName);

    List<Concert> findByTicketPriceLessThanEqual(double ticketPrice);

    List<Concert> findConcertByTicketPriceBetween(double minPrice, double maxPrice);

    @Query("SELECT c FROM Concert c WHERE c.venue.city = :city")
    List<Concert> findByCity(String city);

    @Query("SELECT c FROM Concert c WHERE c.ticketPrice <= :maxPrice AND c.concertYear >= :earliestYear")
    List<Concert> findByMaxPriceAndMinYear(double maxPrice, int earliestYear);

    @Query("SELECT c.venue.name, SUM(c.ticketPrice * c.ticketsSold) FROM Concert c GROUP BY c.venue.name")
    List<Object[]> getRevenuePerVenue();

    @Query("SELECT c.venue.name, COUNT(c) FROM Concert c GROUP BY c.venue.name ORDER BY COUNT(c) DESC")
    List<Object[]> busiestVenue();

    @Query("SELECT c.artist.name, COUNT(c) FROM Concert c GROUP BY c.artist.name ORDER BY COUNT(c) DESC")
    List<Object[]> busiestArtist();

    @Query("SELECT c.concertYear, AVG(c.ticketPrice) FROM Concert c GROUP BY c.concertYear ORDER BY c.concertYear")
    List<Object[]> averagePriceByYear();
}
