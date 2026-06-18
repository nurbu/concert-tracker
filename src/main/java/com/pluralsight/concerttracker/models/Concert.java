package com.pluralsight.concerttracker.models;

import jakarta.persistence.*;

/**
 * Maps to the Concert table in the database
 */
@Entity
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int concertYear;
    private double ticketPrice;
    private int ticketsSold;

    @ManyToOne(optional = false)
    private Artist artist;

    @ManyToOne(optional = false)
    private Venue venue;

    @ManyToOne(optional = false)
    private Promoter promoter;

    public Concert() {
    }

    public Concert(int concertYear, double ticketPrice, int ticketsSold, Artist artist, Venue venue, Promoter promoter) {
        this.concertYear = concertYear;
        this.ticketPrice = ticketPrice;
        this.ticketsSold = ticketsSold;
        this.artist = artist;
        this.venue = venue;
        this.promoter = promoter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getConcertYear() {
        return concertYear;
    }

    public void setConcertYear(int concertYear) {
        this.concertYear = concertYear;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Promoter getPromoter() {
        return promoter;
    }

    public void setPromoter(Promoter promoter) {
        this.promoter = promoter;
    }

    @Override
    public String toString() {
        return "Venue: " + venue.getName() + " Artist: " + artist.getName() + " Concert year: " + concertYear + " Ticket Price: $" + ticketPrice + " Tickets Sold: " + ticketsSold;
    }
}
