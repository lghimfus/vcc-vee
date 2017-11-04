package com.lghimfus.vee.models;

/**
 * This class is a model object that contains the data of a location.
 * 
 * @author lghimfus
 *
 */
public class Location {
    /*
     * xCoord represents the coordinate on the X axis of the "world".
     * yCoord represents the coordinate on the Y axis of the "world".
     */
    private final int xCoord;
    private final int yCoord;

    public Location (int x, int y) {
        this.xCoord = x;
        this.yCoord = y;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    @Override
    public String toString() {
        return String.format("Location: (%d, %d)", xCoord, yCoord);
    }

}
