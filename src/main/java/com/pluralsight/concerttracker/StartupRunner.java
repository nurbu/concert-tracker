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
            System.out.println("5: Search Concerts");
            System.out.println("6: Manage Reports");
            System.out.println("0: Quit");
            System.out.println("Enter choice(0-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> concertScreen();
                case 2 -> venueScreen();
                case 3 -> artistScreen();
                case 4 -> promoterScreen();
                case 5 -> searchScreen();
                case 6 -> reportScreen();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void reportScreen() {
        boolean running = true;
        while (running) {
            System.out.println("Report Screen\n");
            System.out.println("1: Revenue per venue");
            System.out.println("2: Busiest Venue and Artist");
            System.out.println("3: Average Ticket Price by Year");
            System.out.println("0: Quit");
            System.out.println("Enter choice(0-1): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> revenuePerVenue();
                case 2 -> busiestVenueAndArtist();
                case 3 -> averagePriceByYear();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void averagePriceByYear() {
        List<Object[]> average = concertService.averagePriceByYear();

        if (average.isEmpty()) {
            System.out.println("There are no available concerts");
            return;
        }
        for (Object[] row : average) {
            int year = (Integer) row[0];
            double ticketPrice = ((Double) row[1]).doubleValue();
            System.out.println("Concert " + year + ": " + ticketPrice);
        }
    }

    private void busiestVenueAndArtist() {
        List<Object[]> venue = concertService.busiestVenue();
        List<Object[]> artist = concertService.busiestArtist();

        if (venue.isEmpty() || artist.isEmpty()) {
            System.out.println("Venue or Artist are empty");
            return;
        }
        String busiestVenue = (String) venue.get(0)[0];
        long venueConcerts = (long) venue.get(0)[1];
        String busiestArtist = (String) artist.get(0)[0];
        long artistConcerts = (long) artist.get(0)[1];
        System.out.println("Venue: " + busiestVenue + "Total Concerts: " + venueConcerts);
        System.out.println("Busiest Artist: " + busiestArtist + " Total Concerts: " + artistConcerts);
    }

    private void revenuePerVenue() {
        List<Object[]> report = concertService.revenuePerVenue();
        if (report.isEmpty()) {
            System.out.println("There are no Concert entries for this Venue");
            return;
        }
        for (Object[] row : report) {
            String name = (String) row[0];
            double revenue = ((Number) row[1]).doubleValue();
            System.out.println(name + " $" + revenue);
        }
    }

    private void searchScreen() {
        boolean running = true;
        while (running) {
            System.out.println("Search Concerts Screen\n");
            System.out.println("Search By:");
            System.out.println("1: Year");
            System.out.println("2: Artist");
            System.out.println("3: Venues");
            System.out.println("4: City");
            System.out.println("5: Max Price");
            System.out.println("6: Price Range");
            System.out.println("7: Max price and earliest year");
            System.out.println("0: Quit");
            System.out.println("Enter choice(0-7): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> filterByYear();
                case 2 -> filterByArtist();
                case 3 -> filterByVenue();
                case 4 -> filterByCity();
                case 5 -> filterByMaxPrice();
                case 6 -> filterByPriceRange();
                case 7 -> filterByMaxPriceAndEarliestYear();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void filterByYear() {
        System.out.println("Enter Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        List<Concert> concerts = concertService.searchConcertByYear(year);
        if (concerts.isEmpty()) {
            System.out.println("Concert not found");
            return;
        }
        for (Concert concert : concerts) {
            System.out.println(concert);
        }
    }

    private void filterByArtist() {
        System.out.println("Enter Artist Name: ");
        String artistName = scanner.nextLine();

        List<Concert> concerts = concertService.searchConcertByArtistName(artistName);
        if (concerts.isEmpty()) {
            System.out.println("Concert not found for artist: " + artistName);
            return;
        }
        for (Concert concert : concerts) {
            System.out.println(concert);
        }

    }

    private void filterByVenue() {
        System.out.println("Enter Venue Name: ");
        String venueName = scanner.nextLine();

        List<Concert> concerts = concertService.searchConcertByVenueName(venueName);
        if (concerts.isEmpty()) {
            System.out.println("Concert not found for venue: " + venueName);
            return;
        }
        for (Concert concert : concerts) {
            System.out.println(concert);
        }
    }

    private void filterByCity() {
        System.out.println("Enter City Name: ");
        String cityName = scanner.nextLine();

        List<Concert> concerts = concertService.searchConcertByCity(cityName);
        if (concerts.isEmpty()) {
            System.out.println("Concert not found for city: " + cityName);
            return;
        }
        for (Concert concert : concerts) {
            System.out.println(concert);
        }
    }

    private void filterByMaxPrice() {
        System.out.println("Enter Max Ticket Price: ");
        double maxPrice = scanner.nextDouble();
        scanner.nextLine();

        List<Concert> concerts = concertService.searchConcertByMaxTicketPrice(maxPrice);
        if (concerts.isEmpty()) {
            System.out.println("Concert not found for less than or equal to Max Ticket Price: " + maxPrice);
            return;
        }
        for (Concert concert : concerts) {
            System.out.println(concert);
        }

    }

    private void filterByPriceRange() {
        System.out.println("Enter Min Ticket Price: ");
        double minPrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter Max Ticket Price: ");
        double maxPrice = scanner.nextDouble();
        scanner.nextLine();
        List<Concert> concerts = concertService.searchConcertByTicketPriceRange(minPrice, maxPrice);
        if (concerts.isEmpty()) {
            System.out.println("Concert not found for price range: " + minPrice + " to " + maxPrice);
            return;
        }
        for (Concert concert : concerts) {
            System.out.println(concert);
        }
    }

    private void filterByMaxPriceAndEarliestYear() {
        System.out.println("Enter Max Ticket Price: ");
        double maxPrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter Earliest Year: ");
        int earliestYear = scanner.nextInt();
        scanner.nextLine();

        List<Concert> concerts = concertService.searchConcertByMaxPriceAndEarliestYear(maxPrice, earliestYear);
        if (concerts.isEmpty()) {
            System.out.println("Concert not found for max price: " + maxPrice + " and earliest year: " + earliestYear);
        }
        for (Concert concert : concerts) {
            System.out.println(concert);
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
        System.out.println("Enter Venue Id: ");
        long venueID = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter new capacity: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();

        concertService.updateVenueCapacity(venueID, capacity);
        System.out.println("Venue has been updated");
    }

    private void deleteAVenue() {
        System.out.println("Enter Venue Id: ");
        long venueID = scanner.nextLong();
        scanner.nextLine();

        concertService.deleteVenue(venueID);
        System.out.println("Venue has been deleted");
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

    // Artist

    private void artistScreen() {

        boolean running = true;
        while (running) {
            System.out.println("Artist Screen\n");
            System.out.println("1: Add Artist");
            System.out.println("2: View All Artists");
            System.out.println("3: Update Artist Genre");
            System.out.println("4: Delete Artist");
            System.out.println("5: Find Artist By Genre");
            System.out.println("6: Find Artist By Name");
            System.out.println("0: Exit");
            System.out.println("Enter choice(0-6): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> addAnArtist();
                    case 2 -> viewAllArtists();
                    case 3 -> updateArtistGenre();
                    case 4 -> deleteAnArtist();
                    case 5 -> findArtistByGenre();
                    case 6 -> findArtistByName();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid choice");
                }
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void addAnArtist() {
        System.out.println("Enter Artist Name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Artist Genre: ");
        String genre = scanner.nextLine();

        concertService.addArtist(new Artist(name, genre));
        System.out.println("Artist has been added");
    }

    private void viewAllArtists() {
        List<Artist> artists = concertService.findAllArtists();
        if (artists.isEmpty()) {
            System.out.println("No Artists found");
            return;
        }
        for (Artist artist : artists) {
            System.out.println(artist);
        }
    }

    private void updateArtistGenre() {
        System.out.println("Enter Artist ID: ");
        long artistId = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter new Artist Genre: ");
        String newGenre = scanner.nextLine();

        concertService.updateArtistGenre(artistId, newGenre);
        System.out.println("Artist has been updated");
    }

    private void deleteAnArtist() {
        System.out.println("Enter Artist ID: ");
        long artistId = scanner.nextLong();
        scanner.nextLine();

        concertService.deleteArtist(artistId);
        System.out.println("Artist has been deleted");
    }

    private void findArtistByGenre() {
        System.out.println("Enter Artist Genre: ");
        String genre = scanner.nextLine();
        List<Artist> artists = concertService.findAllArtistsByGenre(genre);
        if (artists.isEmpty()) {
            System.out.println("No Artists found");
            return;
        }
        for (Artist artist : artists) {
            System.out.println(artist);
        }
    }

    private void findArtistByName() {
        System.out.println("Enter Artist Name(Part of it works): ");
        String name = scanner.nextLine();
        List<Artist> artists = concertService.findArtistByName(name);
        if (artists.isEmpty()) {
            System.out.println("No Artists found");
            return;
        }
        for (Artist artist : artists) {
            System.out.println(artist);
        }
    }

    // Promoter
    private void promoterScreen() {
        boolean running = true;
        while (running) {
            System.out.println("Promoter Screen\n");
            System.out.println("1: Add Promoter");
            System.out.println("2: View All Promoters");
            System.out.println("3: Delete A Promoter");
            System.out.println("4: Find A Promoter by name");
            System.out.println("0: Quit");
            System.out.println("Enter choice(0-4): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addAPromoter();
                case 2 -> viewAllPromoters();
                case 3 -> deleteAPromoter();
                case 4 -> findAPromoterByName();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void addAPromoter() {
        System.out.println("Enter Promoter Name: ");
        String name = scanner.nextLine();

        concertService.addPromoter(new Promoter(name));
        System.out.println("Promoter has been added");
    }

    private void viewAllPromoters() {
        List<Promoter> promoters = concertService.findAllPromoters();
        if (promoters.isEmpty()) {
            System.out.println("No Promoters found");
            return;
        }
        for (Promoter promoter : promoters) {
            System.out.println(promoter);
        }
    }

    private void deleteAPromoter() {
        System.out.println("Enter Promoter ID: ");
        long promoterId = scanner.nextLong();
        scanner.nextLine();

        concertService.deletePromoter(promoterId);
        System.out.println("Promoter has been deleted");
    }

    private void findAPromoterByName() {
        System.out.println("Enter Promoter Name: ");
        String name = scanner.nextLine();
        List<Promoter> promoters = concertService.findAllPromotersByName(name);
        if (promoters.isEmpty()) {
            System.out.println("No Promoters found");
            return;
        }
        for (Promoter promoter : promoters) {
            System.out.println(promoter);
        }
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
