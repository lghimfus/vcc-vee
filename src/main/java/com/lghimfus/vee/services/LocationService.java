package com.lghimfus.vee.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.lghimfus.vee.models.Location;

/**
 * This class encapsulates the business logic of a location.
 * This class consists of static methods that handles requests from a client.
 * 
 * @author lghimfus
 *
 */
public class LocationService {
    
    // Range parameters of the "world".
    private static final int MAX_X = 10;
    private static final int MIN_X = -10;
    private static final int MAX_Y = 10;
    private static final int MIN_Y = -10;

    /**
     * Generates "empty" locations that can hold events.
     * Adds them to a list to keep track of available locations.
     * 
     * Locations are generated in the given range i.e from MIN_X to MAX_X for
     * X axis and from MIN_Y to MAX_Y for Y axis. 
     * 
     * @return a list of "empty" locations.
     * 
     */
    public static List<Location> createLocations() {
        List<Location> locations = new ArrayList<>();
        
        // For each position in the "world", create a new available location.
        for (int xCoord = MIN_X; xCoord <= MAX_X; xCoord++) {
            for (int yCoord = MIN_Y; yCoord <= MAX_Y; yCoord++)
                locations.add(new Location(xCoord, yCoord));
        }
        
        return locations;
    }
    
    /**
     * Gets and removes a random location from the list of locations.
     * Only available locations are kept in the list.
     * 
     * If we wanted the location to be available for multiple events, we would 
     * have keep it in the list and verified if it can hold any more events each 
     * time the location it was retrieved.
     * 
     * @return a random location from the list of available locations.
     */
    public static Location getRandomAvailableLocation(List<Location> locs) {
        int randomId = ThreadLocalRandom.current().nextInt(locs.size());
        
        return locs.remove(randomId);        
    }
    
    /**
     * Calculates the distance between the input location and the location of a 
     * certain event in the generated list.
     * 
     * Manhattan distance is used.
     * 
     * @param inputLocation a location at the input coordinates.
     * @param eventLocation the location of an event from the generated list.
     * 
     * @return the distance between two locations.
     */
    public static int calculateDistance(Location inputLocation, Location eventLocation) {
        return Math.abs(inputLocation.getxCoord() - eventLocation.getxCoord()) 
               + Math.abs((inputLocation.getyCoord() - eventLocation.getyCoord()));
    }
    
}
