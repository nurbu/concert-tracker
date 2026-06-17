package com.pluralsight.concerttracker;

import com.pluralsight.concerttracker.models.Artist;
import com.pluralsight.concerttracker.models.Concert;
import com.pluralsight.concerttracker.models.Promoter;
import com.pluralsight.concerttracker.models.Venue;
import com.pluralsight.concerttracker.service.ConcertService;
import com.pluralsight.concerttracker.service.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class StartupRunner implements CommandLineRunner {


    private final ConcertService concertService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public StartupRunner(ConcertService concertService) {
        this.concertService = concertService;
    }

    @Override
    public void run(String... args) {
        seedData();
        homeScreen();

    }

    private void homeScreen() {
        boolean running = true;
        while (running) {
            System.out.println("Home Screen\n");
            System.out.println("1: Manage Concerts");
            System.out.println("2: Manage Venues");
            System.out.println("3: Manage Artists");
            System.out.println("4: Manage Promoters");
            System.out.println("0: Quit");
            System.out.println("Enter choice(0-4): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> concertScreen();
                case 2 -> venueScreen();
                case 3 -> artistScreen();
                case 4 -> promoterScreen();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void concertScreen() {
        boolean running = true;
        while (running) {
            System.out.println("Concert Screen\n");
            System.out.println("1: Add a Concert");
            System.out.println("2: View a Concert");
            System.out.println("3: Update a Concert's ticket price");
            System.out.println("4: Update a Concert's tickets sold count");
            System.out.println("5: Delete a Concert");
            System.out.println("0: Quit");
            System.out.println("Enter choice(0-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> addAConcert();
                    case 2 -> viewAConcert();
                    case 3 -> updateAConcertTicketPrice();
                    case 4 -> updateAConcertTicketSold();
                    case 5 -> deleteAConcert();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid choice");
                }
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void addAConcert() {

        System.out.println("Artists available");
        concertService.findAllArtists().forEach(artist -> System.out.println(artist.getId() + ": " + artist.getName()));
        System.out.println("Enter Artist ID: ");
        long artistId = scanner.nextLong();
        scanner.nextLine();
        Artist artist = concertService.findArtistByID(artistId);

        System.out.println("Venues available");
        concertService.findAllVenues().forEach(venue -> System.out.println(venue.getId() + ": " + venue.getName()));
        System.out.println("Enter Artist ID: ");
        long venueId = scanner.nextLong();
        scanner.nextLine();
        Venue venue = concertService.findVenueByID(venueId);

        System.out.println("Promoter available");
        concertService.findAllPromoters().forEach(promoter -> System.out.println(promoter.getId() + ": " + promoter.getName()));
        System.out.println("Enter Promoter ID: ");
        long promoterId = scanner.nextLong();
        scanner.nextLine();
        Promoter promoter = concertService.findPromoterByID(promoterId);
        boolean running = true;
        int concertYear = 0;
        while (running) {
            System.out.println("Enter the Concert Year: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Enter a Proper Year after 2000");
                continue;
            }
            concertYear = scanner.nextInt();
            scanner.nextLine();
            if (concertYear < 2000) {
                System.out.println("Invalid Year");
                continue;
            }
            running = false;
        }
        running = true;
        double ticketPrice = 0;
        while (running) {
            System.out.println("Enter Concert Ticket Price: ");
            if (!scanner.hasNextDouble()) {
                System.out.println("Enter a Number");
                continue;
            }
            ticketPrice = scanner.nextDouble();
            scanner.nextLine();
            if (ticketPrice < 0) {
                System.out.println("Put a Ticket Price greater than 0");
                continue;
            }
            running = false;
        }
        running = true;
        int ticketsSold = 0;
        while (running) {
            System.out.println("Enter Concert Tickets Sold: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Enter a Number");
                continue;
            }
            ticketsSold = scanner.nextInt();
            scanner.nextLine();
            if (ticketsSold < 0) {
                System.out.println("Enter ticket sales greater than 0");
                continue;
            }
            running = false;
        }


        concertService.addConcert(new Concert(concertYear, ticketPrice, ticketsSold, artist, venue, promoter));
        System.out.println("Concert has been added");
    }

    private void viewAConcert() {
        System.out.println("Enter Concert ID: ");
        long concertID = scanner.nextInt();
        scanner.nextLine();

        Concert concert = concertService.findConcertById(concertID);
        System.out.println("Concert: " + concert);
    }

    private void updateAConcertTicketPrice() {
        System.out.println("Enter Concert ID: ");
        long concertID = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter new ticket price: ");
        double ticketPrice = scanner.nextDouble();
        scanner.nextLine();

        concertService.updateConcertPrice(concertID, ticketPrice);
        System.out.println("Ticket Price has been updated");
    }

    private void updateAConcertTicketSold() {
        System.out.println("Enter Concert ID: ");
        long concertID = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter new ticket sold count: ");
        int ticketsSold = scanner.nextInt();
        scanner.nextLine();

        concertService.updateConcertTicketsSold(concertID, ticketsSold);
        System.out.println("Ticket Sold has been updated");
    }

    private void deleteAConcert() {
        System.out.println("Enter Concert ID: ");
        long concertID = scanner.nextLong();
        scanner.nextLine();

        concertService.deleteConcert(concertID);
        System.out.println("Concert has been deleted");
    }

    private void listAllConcerts() {
        List<Concert> concerts = concertService.findAllConcerts();
        if (concerts.isEmpty()) {
            System.out.println("No concerts found");
            return;
        }
        for (Concert concert : concerts) {
            System.out.println(concert);
        }
    }


    // Venue
    private void venueScreen() {
        boolean running = true;
        while (running) {
            System.out.println("Venues Screen\n");
            System.out.println("1: Add a Venue");
            System.out.println("2: View all Venues");
            System.out.println("3: Update a Venue's capacity");
            System.out.println("4: Delete a Venue");
            System.out.println("5: View a Venue by city");
            System.out.println("6: View a Venue by name");
            System.out.println("7: Find venues by min capacity");
            System.out.println("0: Quit");

            System.out.println("Enter choice(0-7): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> addAVenue();
                    case 2 -> viewAllVenues();
                    case 3 -> updateAVenueCapacity();
                    case 4 -> deleteAVenue();
                    case 5 -> viewAVenueByCity();
                    case 6 -> viewAVenueByName();
                    case 7 -> findVenueByMinCapacity();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid choice");
                }
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Artist

    private void addAVenue() {
        System.out.println("Enter Venue Name: ");
        String venueName = scanner.nextLine();
        System.out.println("Enter Venue City: ");
        String city = scanner.nextLine();
        System.out.println("Enter Venue Capacity: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();

        concertService.addVenue(new Venue(venueName, city, capacity));
        System.out.println("Venue has been added");
    }

    private void viewAllVenues() {

        List<Venue> venues = concertService.findAllVenues();
        if (venues.isEmpty()) {
            System.out.println("No Venues found");
            return;
        }
        for (Venue venue : venues) {
            System.out.println(venue);
        }
    }

    private void updateAVenueCapacity() {
    }

    private void deleteAVenue() {
    }

    private void viewAVenueByCity() {
        System.out.println("Enter Venue City: ");
        String city = scanner.nextLine();
        List<Venue> venues = concertService.findVenuesByCity(city);
        if (venues.isEmpty()) {
            System.out.println("No Venues found in city: " + city);
            return;
        }
        for (Venue venue : venues) {
            System.out.println(venue);
        }
    }

    private void viewAVenueByName() {
        System.out.println("Enter Venue Name(): ");
        String venueName = scanner.nextLine();
        List<Venue> venues = concertService.findVenuesByName(venueName);
        if (venues.isEmpty()) {
            System.out.println("No Venues found in venue name: " + venueName);
            return;
        }
        for (Venue venue : venues) {
            System.out.println(venue);
        }
    }

    private void findVenueByMinCapacity() {
        System.out.println("Enter Venue Min Capacity: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();
        List<Venue> venues = concertService.findVenuesByMinCapacity(capacity);
        if (venues.isEmpty()) {
            System.out.println("No Venues found");
            return;
        }
        for (Venue venue : venues) {
            System.out.println(venue);
        }
    }


    private void artistScreen() {
    }

    // Promoter
    private void promoterScreen() {
    }


    // Seed Data

    private void seedData() {
        if (!concertService.findAllConcerts().isEmpty()) {
            return;
        }
        Venue venue = concertService.addVenue(new Venue("Barclays Center", "Brooklyn", 19000));
        Venue pc = concertService.addVenue(new Venue("Prudential Center", "Newark", 18000));
        Venue msg = concertService.addVenue(new Venue("Madison Square Garden", "New York", 20000));

        Artist mj = concertService.addArtist(new Artist("Michael Jackson", "Pop"));
        Artist d = concertService.addArtist(new Artist("Drake", "Hip-hop"));
        Artist btr = concertService.addArtist(new Artist("Big Time Rush", "Pop"));

        Promoter ln = concertService.addPromoter(new Promoter("Live Nation Entertainment"));
        Promoter aegP = concertService.addPromoter(new Promoter("AEG Presents"));

        concertService.addConcert(new Concert(2024, 89.99, 15000, mj, venue, ln));
        concertService.addConcert(new Concert(2024, 75.50, 12000, d, pc, aegP));
        concertService.addConcert(new Concert(2025, 65.00, 18500, btr, msg, ln));
        concertService.addConcert(new Concert(2023, 95.00, 17000, mj, msg, aegP));
        concertService.addConcert(new Concert(2025, 55.25, 14000, d, venue, ln));
        concertService.addConcert(new Concert(2024, 70.00, 16000, btr, pc, aegP));
        concertService.addConcert(new Concert(2023, 80.00, 19000, mj, venue, ln));
        concertService.addConcert(new Concert(2025, 60.00, 13000, d, msg, aegP));
        concertService.addConcert(new Concert(2024, 99.99, 18000, btr, msg, ln));
        concertService.addConcert(new Concert(2023, 72.50, 15500, mj, pc, aegP));
        concertService.addConcert(new Concert(2025, 68.00, 17500, d, msg, ln));
        concertService.addConcert(new Concert(2024, 50.00, 16500, btr, venue, aegP));
    }
}
