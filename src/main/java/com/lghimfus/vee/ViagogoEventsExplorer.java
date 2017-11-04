package com.lghimfus.vee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.lghimfus.vee.models.Event;
import com.lghimfus.vee.models.Location;
import com.lghimfus.vee.models.Ticket;
import com.lghimfus.vee.services.EventService;
import com.lghimfus.vee.services.LocationService;
import com.lghimfus.vee.services.TicketService;

/**
 * This class accepts a user location as a pair of coordinates, and returns a 
 * list of the five closest events, along with the cheapest ticket price for 
 * each event.
 * 
 * Viagogo coding challenge.
 * 
 * @author lghimfus
 *
 */
public class ViagogoEventsExplorer {

    // Range parameters of the "world"
    private static final int MAX_POS_X = 10;
    private static final int MAX_NEG_X = -10;
    private static final int MAX_POS_Y = 10;
    private static final int MAX_NEG_Y = -10;
    
    public static void main(String[] args) {
        
        // Populates the world with empty locations.
        List<Location> locations = LocationService.createLocations();
        
        // Generates between 100 and 150 events at random available locations.
        List<Event> eventsList = EventService.generateEvents(locations, 100, 150);
        
        // Gets the location at the input coordinates.
        Location inputLocation = readInputLocation();
        
        // Gets the desired closest events to the input coordinates.
        Map<Event, Integer> closestEvents = 
                EventService.getClosestEvents(5, inputLocation, eventsList);
        
        System.out.println(String.format("\n\nClosest events to (%d,%d):\n",
                inputLocation.getxCoord(), inputLocation.getyCoord()));
        
        // Prints out each event using the required format.
        closestEvents.entrySet().forEach(map -> {
            Event currentEvent = map.getKey(); 
            int distanceToInputCoord = map.getValue();
            
            String ticketFormat;
            /*
             *  If the event has tickets, get the cheapest one, its currency and
             *  price, then format it for printing.
             */
            if (currentEvent.getTickets().isEmpty()) {
                ticketFormat = "no tickets were found";
            }
            else {
                Ticket cheapestTicket = 
                        TicketService.getCheapestTicketOfEvent(currentEvent);
                
                ticketFormat = String.format("%s%05.2f", 
                        cheapestTicket.getCurrencySymbol(),
                        cheapestTicket.getPrice());
            }
                
            System.out.println(
                String.format("Event %03d - %s, Distance %d",
                    currentEvent.getId(),
                    ticketFormat,
                    distanceToInputCoord));
        });
        
    }
    
    /**
     * Helps to read user's input coordinates.
     * 
     * @return a new location at the input coordinates. 
     */
    public static Location readInputLocation() {
        boolean isInputCorrect = false;
        
        /*
         * Tries to fetch user's input until he gets it right.
         * If the user does not want to continue, he can quit the loop anytime.
         */
        while(!isInputCorrect) {
            System.out.print("Please input coordinates: > ");
            
            BufferedReader reader = 
                    new BufferedReader(new InputStreamReader(System.in));
            try {
                // Splits the input by comma and removes white spaces.
                String[] coords = reader.readLine().replaceAll("\\s","").split(",");
                
                if (coords[0].toLowerCase().startsWith("q")) {
                    System.out.println("Bye bye");
                    System.exit(0);
                }

                if (coords.length != 2)
                    throw new IllegalArgumentException("Two coordinates are needed.");
                
                int xCoord = Integer.parseInt(coords[0]);
                int yCoord = Integer.parseInt(coords[1]);
                
                if (xCoord > MAX_POS_X || yCoord > MAX_POS_Y 
                        || xCoord < MAX_NEG_X || yCoord < MAX_NEG_Y)
                    throw new IllegalArgumentException(
                            "Input coordinates were not in the required range.");
                else {
                    isInputCorrect = true;
                    return new Location(xCoord, yCoord);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() +
                        " Coordinates values should be integers between -10 and 10."
                        + " Input q to QUIT.");
            }
            
       } // while
        return null;
    } // readInput
}
