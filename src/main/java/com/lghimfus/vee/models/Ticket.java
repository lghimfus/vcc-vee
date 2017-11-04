package com.lghimfus.vee.models;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * This class is a model object that contains and gives access to the data of 
 * a ticket.
 * 
 * @author lghimfus
 *
 */
public class Ticket implements Comparable<Ticket>{
    private BigDecimal price;
    private Currency currency;
    
    public Ticket(BigDecimal price, Currency currency) {
        this.price = price;
        this.currency = currency;
    }
    
    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }
    
    public String getCurrencySymbol () {
        return this.currency.getSymbol(
                new Locale.Builder().setLanguage("en").setRegion("US").build());
    }
    
    /**
     * Compares two tickets by price.
     * 
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Ticket ticket) {
        if (this.getPrice().compareTo(ticket.getPrice()) < 0)
            return -1;
        else if (this.getPrice().compareTo(ticket.getPrice()) > 0)
            return 1;
        else
            return 0;
    }   
    
    @Override
    public String toString() {
        return String.format("Ticket: %05.2f %s", price, currency);
    }
    
}