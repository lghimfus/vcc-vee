package com.lghimfus.vee.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.lghimfus.vee.models.Event;
import com.lghimfus.vee.models.Location;
import com.lghimfus.vee.models.Ticket;

/**
 * This class encapsulates the business logic of an event.
 * This class consists of static methods that handles requests from a client.
 * 
 * @author lghimfus
 *
 */
public class EventService {
    
    /**
     * Generates a random list of random events and adds them to the "world".
     * Generates between rangeMin and rangeMax events.
     * 
     * @param locations the list of empty locations to spawn events at.
     * @param rangeMin minimum number of events to be generated.
     * @param rangeMax maximum number of events to be generated.
     * 
     * @return a list of random events.
     */
    public static List<Event> generateEvents(List<Location> locations, 
            int rangeMin, int rangeMax) {
 
        int numberOfEvents = 
                ThreadLocalRandom.current().nextInt(rangeMin, rangeMax);
        
        List<Event> eventsList = new ArrayList<Event>();
        
        // Populates the world with events at available locations.
        while (eventsList.size() != numberOfEvents) {       
            Location randomLocation = 
                    LocationService.getRandomAvailableLocation(locations);
            List<Ticket> tickets = TicketService.generateTickets();
            
            Event currentEvent = new Event(randomLocation, tickets);
            eventsList.add(currentEvent);
        }
        
        return eventsList;
    }
    
    /**
     * Calculates distances between events and the input coordinates.
     * 
     * Finds the closest events to the input coordinates.
     * Makes sure there are enough events to be found, otherwise returns all 
     * available events.
     * 
     * @param eventsToFind The number of closest events the user wants to find.
     * @param inputLocation The input location coordinates.
     * @param eventsList The list of available events.
     * 
     * @return a Map<Event, Integer> of the closest events to the input 
     *         coordinates alongside with the distances between events and the 
     *         input location.
     * 
     */
    public static Map<Event, Integer> getClosestEvents(int eventsToFind, 
            Location inputLocation, List<Event> eventsList) {
        
        if (eventsList.size() < eventsToFind)
            eventsToFind = eventsList.size();
        
        // Maps an event to its distance to the input location.
        Map<Event, Integer> distances = new HashMap<Event, Integer>();
        
        eventsList.forEach(event -> {
            int distance = LocationService
                    .calculateDistance(inputLocation, event.getLocation());
            distances.put(event, distance);
        });
        
        Map<Event, Integer> closestEvents = new LinkedHashMap<>();
        
        /*
         *  Sorts the events by the distance to the input location.
         *  Grabs the required closest events.
         */
        distances.entrySet().stream()
            .sorted(Map.Entry.<Event, Integer>comparingByValue())
            .limit(eventsToFind)
            .forEachOrdered(x -> {
                closestEvents.put(x.getKey(), x.getValue());
            });
        
        return closestEvents;
    }
   
}
