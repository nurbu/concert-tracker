package com.pluralsight.concerttracker;

import com.pluralsight.concerttracker.models.Artist;
import com.pluralsight.concerttracker.models.Concert;
import com.pluralsight.concerttracker.models.Promoter;
import com.pluralsight.concerttracker.models.Venue;
import com.pluralsight.concerttracker.service.ConcertService;
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

            switch (choice) {
                case 1 -> addAConcert();
                case 2 -> viewAConcert();
                case 3 -> updateAConcertTicketPrice();
                case 4 -> updateAConcertTicketSold();
                case 5 -> deleteAConcert();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void addAConcert() {
    }

    private void viewAConcert() {
    }

    private void updateAConcertTicketPrice() {
    }

    private void updateAConcertTicketSold() {
    }

    private void deleteAConcert() {
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
    }

    // Artist
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
