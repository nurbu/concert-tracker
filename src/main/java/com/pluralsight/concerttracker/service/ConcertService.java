package com.pluralsight.concerttracker.service;

import com.pluralsight.concerttracker.data.ArtistRepository;
import com.pluralsight.concerttracker.data.ConcertRepository;
import com.pluralsight.concerttracker.data.PromoterRepository;
import com.pluralsight.concerttracker.data.VenueRepository;
import com.pluralsight.concerttracker.models.Artist;
import com.pluralsight.concerttracker.models.Concert;
import com.pluralsight.concerttracker.models.Promoter;
import com.pluralsight.concerttracker.models.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    private final VenueRepository venueRepository;
    private final ArtistRepository artistRepository;
    private final PromoterRepository promoterRepository;
    private final ConcertRepository concertRepository;

    @Autowired
    public ConcertService(VenueRepository venueRepository, ArtistRepository artistRepository, PromoterRepository promoterRepository, ConcertRepository concertRepository) {
        this.venueRepository = venueRepository;
        this.artistRepository = artistRepository;
        this.promoterRepository = promoterRepository;
        this.concertRepository = concertRepository;

    }

    /**
     *
     * Concerts Functions
     *
     */

    public List<Concert> findAllConcerts() {
        return concertRepository.findAll();
    }

    public Concert findConcertById(Long id) {
        return concertRepository.findById(id).orElseThrow(() -> new NotFoundException("Concert", id));
    }

    public Concert updateConcertPrice(long id, double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Ticket price can't be negative.");
        }
        Concert concert = findConcertById(id);
        concert.setTicketPrice(newPrice);
        return concertRepository.save(concert);
    }

    public Concert updateConcertTicketsSold(long id, int newTicketsSold) {
        Concert concert = findConcertById(id);
        if (newTicketsSold < 0) {
            throw new IllegalArgumentException("Tickets sold can't be negative.");
        }
        if (newTicketsSold > concert.getVenue().getCapacity()) {
            throw new IllegalArgumentException("Tickets sold can't be more than capacity of "
                    + concert.getVenue().getCapacity() + ".");
        }
        concert.setTicketsSold(newTicketsSold);
        return concertRepository.save(concert);
    }

    public void deleteConcert(long id) {
        if (!concertRepository.existsById(id)) {
            throw new NotFoundException("concert", id);
        }
        concertRepository.deleteById(id);
    }

    public Concert addConcert(Concert concert) {
        return concertRepository.save(concert);
    }

    public List<Concert> searchConcertByYear(int year) {
        return concertRepository.findByConcertYear(year);
    }

    public List<Concert> searchConcertByArtistName(String artistName) {
        return concertRepository.findByArtist_NameContaining(artistName);
    }

    public List<Concert> searchConcertByVenueName(String venueName) {
        return concertRepository.findByVenue_NameContaining(venueName);
    }

    public List<Concert> searchConcertByMaxTicketPrice(double maxTicketPrice) {
        return concertRepository.findByTicketPriceLessThanEqual(maxTicketPrice);
    }

    public List<Concert> searchConcertByTicketPriceRange(double minTicketPrice, double maxTicketPrice) {
        return concertRepository.findConcertByTicketPriceBetween(minTicketPrice, maxTicketPrice);
    }

    public List<Concert> searchConcertByCity(String city) {
        return concertRepository.findByCity(city);
    }

    public List<Concert> searchConcertByMaxPriceAndEarliestYear(double maxPrice, int earliestYear) {
        return concertRepository.findByMaxPriceAndMinYear(maxPrice, earliestYear);
    }

    public List<Object[]> revenuePerVenue() {
        return concertRepository.getRevenuePerVenue();
    }

    public List<Object[]> busiestVenue() {
        return concertRepository.busiestVenue();
    }

    public List<Object[]> busiestArtist() {
        return concertRepository.busiestArtist();
    }

    public List<Object[]> averagePriceByYear() {
        return concertRepository.averagePriceByYear();
    }

    /**
     *
     * Artist Functions
     *
     */

    public List<Artist> findAllArtists() {
        return artistRepository.findAll();
    }

    public Artist findArtistByID(long id) {
        return artistRepository.findById(id).orElseThrow(() -> new NotFoundException("artist", id));
    }

    public Artist addArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public List<Artist> findAllArtistsByGenre(String genre) {
        return artistRepository.findByGenre(genre);
    }

    public List<Artist> findArtistByName(String name) {
        return artistRepository.findByNameContainingIgnoreCase(name);
    }

    public Artist updateArtistGenre(long id, String genre) {
        Artist artist = findArtistByID(id);
        artist.setGenre(genre);
        return artistRepository.save(artist);
    }

    public void deleteArtist(long id) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new NotFoundException("artist", id));
        List<Concert> concerts = concertRepository.findByArtist(artist);
        if (!concerts.isEmpty()) {
            throw new IllegalArgumentException("Artist still booked can't be deleted.");
        }
        artistRepository.deleteById(id);
    }

    /**
     *
     * Venue Functions
     *
     */

    public List<Venue> findAllVenues() {
        return venueRepository.findAll();
    }

    public Venue findVenueByID(long id) {
        return venueRepository.findById(id).orElseThrow(() -> new NotFoundException("venue", id));
    }

    public List<Venue> findVenuesByCity(String city) {
        return venueRepository.findByCity(city);
    }

    public List<Venue> findVenuesByName(String name) {
        return venueRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Venue> findVenuesByMinCapacity(int capacity) {
        return venueRepository.findByCapacityGreaterThanEqual(capacity);
    }

    public Venue addVenue(Venue venue) {
        if (venue.getCapacity() < 0) {
            throw new IllegalArgumentException("Venue capacity can't be negative.");
        }

        return venueRepository.save(venue);
    }

    public Venue updateVenueCapacity(long id, int newCapacity) {
        if (newCapacity < 0) {
            throw new IllegalArgumentException("Capacity can't be negative.");
        }
        Venue venue = findVenueByID(id);
        List<Concert> concertsAtVenue = concertRepository.findByVenue(venue);
        for (Concert concert : concertsAtVenue) {
            if (concert.getTicketsSold() > newCapacity) {
                throw new IllegalArgumentException("Tickets sold can't be more than " + newCapacity + ".");
            }
        }
        venue.setCapacity(newCapacity);
        return venueRepository.save(venue);

    }

    public void deleteVenue(long id) {
        if (!venueRepository.existsById(id)) {
            throw new NotFoundException("venue", id);
        }
        Venue venue = findVenueByID(id);
        List<Concert> concertsAtVenue = concertRepository.findByVenue(venue);
        if (!concertsAtVenue.isEmpty()) {
            throw new IllegalStateException("Venue can't be deleted, concerts still booked at venue");
        }
        venueRepository.deleteById(id);
    }

    /**
     *
     * Promoter Functions
     *
     */

    public List<Promoter> findAllPromoters() {
        return promoterRepository.findAll();
    }

    public Promoter findPromoterByID(long id) {
        return promoterRepository.findById(id).orElseThrow(() -> new NotFoundException("promoter", id));
    }

    public List<Promoter> findAllPromotersByName(String name) {
        return promoterRepository.findByNameContainingIgnoreCase(name);
    }

    public Promoter addPromoter(Promoter promoter) {
        return promoterRepository.save(promoter);
    }

    public void deletePromoter(long id) {
        if (!promoterRepository.existsById(id)) {
            throw new NotFoundException("promoter", id);
        }
        Promoter promoter = findPromoterByID(id);
        List<Concert> concerts = concertRepository.findByPromoter(promoter);
        if (!concerts.isEmpty()) {
            throw new IllegalArgumentException("Promoter still booked can't be deleted.");
        }
        promoterRepository.deleteById(id);
    }

}
