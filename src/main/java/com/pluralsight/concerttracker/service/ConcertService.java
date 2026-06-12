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

    public List<Venue> findAllVenues() {
        return venueRepository.findAll();
    }

    public Venue addVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    public List<Artist> findAllArtists() {
        return artistRepository.findAll();
    }

    public Artist addArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public List<Promoter> findAllPromoters() {
        return promoterRepository.findAll();
    }

    public Promoter addPromoter(Promoter promoter) {
        return promoterRepository.save(promoter);
    }

    public List<Concert> findAllConcerts() {
        return concertRepository.findAll();
    }

    public Concert addConcert(Concert concert) {
        return concertRepository.save(concert);
    }
}
