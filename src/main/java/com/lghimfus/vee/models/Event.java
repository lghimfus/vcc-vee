package com.lghimfus.vee.models;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is a model object that contains and gives access to the data of 
 * an event.
 * 
 * @author lghimfus
 *
 */
public class Event {
    /*
     *  This is a global id used to provide uniques ids for created events.
     *  The global id is incremented each time an event is created.
     */
    private static AtomicInteger globalId = new AtomicInteger();
    
    private final int id;
    private Location location;
    private List<Ticket> tickets;
    
    public Event(Location location, List<Ticket> tickets) {
        this.id = globalId.incrementAndGet();
        this.location = location;
        this.tickets = tickets;
    }
    
    public Location getLocation() {
        return location;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
    
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Event %03d", id);
    }
}