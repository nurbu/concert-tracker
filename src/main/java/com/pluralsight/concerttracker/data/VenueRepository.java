package com.pluralsight.concerttracker.data;

import com.pluralsight.concerttracker.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Derived queries for Venue model
 */
import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    List<Venue> findByCity(String city);

    List<Venue> findByNameContainingIgnoreCase(String name);

    List<Venue> findByCapacityGreaterThanEqual(int capacity);
}
