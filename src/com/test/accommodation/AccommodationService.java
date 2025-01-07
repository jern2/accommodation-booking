package com.test.accommodation;

import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AccommodationService {
    private List<Accommodation> accommodations;

    public AccommodationService() {
        accommodations = new ArrayList<>();
        loadAccommodations();
    }

    private void loadAccommodations() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(".\\data\\accommodation_list.txt"));
            for (String line : lines) {
                accommodations.add(Accommodation.fromFile(line));
            }
        } catch (Exception e) {
            System.err.println("Failed to load accommodations: " + e.getMessage());
        }
    }

    public Accommodation getAccommodationById(int accommodationId) {
        for (Accommodation accommodation : accommodations) {
            if (accommodation.getId() == accommodationId) {
                return accommodation;
            }
        }
        return null;
    }
}



