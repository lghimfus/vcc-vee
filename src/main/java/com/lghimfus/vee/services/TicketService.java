package com.lghimfus.vee.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.lghimfus.vee.models.Event;
import com.lghimfus.vee.models.Ticket;

/**
 * This class encapsulates the business logic of a ticket.
 * This class consists of static methods that handles requests from a client.
 * 
 * @author lghimfus
 *
 */
public class TicketService {
    
    public static final int MAX_TICKETS_N0 = 50;
    public static final double MIN_TICKET_PRICE = 1;
    public static final double MAX_TICKET_PRICE = 50;
    public static final int TICKET_PRICE_DECIMALS = 2;
    public static final Currency TICKET_CURRENCY = Currency.getInstance("USD");
      
    /**
     *  Generates between 0 and MAX_TICKETS_N0 tickets.
     *  Each ticket has a random price between the given range 
     *  i.e [MIN_TICKET_PRICE, MAX_TICKET_PRICE].
     *     
     *  @return a list of tickets with random prices.
     */
    public static List<Ticket> generateTickets() {
        int numberOfTickets = 
                ThreadLocalRandom.current().nextInt(MAX_TICKETS_N0);
        
        List<Ticket> ticketList = new ArrayList<Ticket>();

        while (ticketList.size() != numberOfTickets) {
            ticketList.add(new Ticket(
                    generateTicketPrice(TICKET_PRICE_DECIMALS), TICKET_CURRENCY));
        }
        
        return ticketList;
    }
    
    /**
     * Generates a random ticket price in the given interval 
     * [MIN_TICKET_PRICE, MAX_TICKET_PRICE].
     * 
     * @param scale number of decimals of the price.
     * 
     * @return a random price in the given interval.
     */
    public static BigDecimal generateTicketPrice(int scale) {
        double tempPrice = ThreadLocalRandom.current()
                .nextDouble(MIN_TICKET_PRICE, MAX_TICKET_PRICE);
        
        return new BigDecimal(tempPrice).setScale(scale, RoundingMode.HALF_UP);
    }
    
    /**
     * 
     * @return the cheapest ticket of an event.
     * 
     */
    public static Ticket getCheapestTicketOfEvent(Event event) {
        return event.getTickets().stream()
                    .min(Comparator.comparing(Ticket::getPrice))
                    .orElse(null);
    }
    
}
