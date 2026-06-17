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

    // Concert

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

    // Artist
    public List<Artist> findAllArtists() {
        return artistRepository.findAll();
    }

    public Artist findArtistByID(long id) {
        return artistRepository.findById(id).orElseThrow(() -> new NotFoundException("artist", id));
    }

    public Artist addArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    // Venue

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
        return venueRepository.findByNameContaining(name);
    }

    public Venue addVenue(Venue venue) {
        if (venue.getCapacity() < 0) {
            throw new IllegalArgumentException("Venue capacity can't be negative.");
        }

        return venueRepository.save(venue);
    }

    // Promoter

    public List<Promoter> findAllPromoters() {
        return promoterRepository.findAll();
    }

    public Promoter findPromoterByID(long id) {
        return promoterRepository.findById(id).orElseThrow(() -> new NotFoundException("promoter", id));
    }

    public Promoter addPromoter(Promoter promoter) {
        return promoterRepository.save(promoter);
    }

}
