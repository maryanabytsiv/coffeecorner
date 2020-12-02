package com.epam.coffeecorner.model;

public class StampCard {

    int id;
    int numberOfStampsNeeded;
    int numberOfStampsAlreadyThere;

    public StampCard(int id, int numberOfBeverageForFree) {
        this.id = id;
        this.numberOfStampsNeeded = numberOfBeverageForFree;
    }

    public int getId() {
        return id;
    }

    public int getNumberOfStampsNeeded() {
        return numberOfStampsNeeded;
    }

    public int getNumberOfStampsAlreadyThere() {
        return numberOfStampsAlreadyThere;
    }

    public void setNumberOfStampsAlreadyThere(int numberOfStampsAlreadyThere) {
        this.numberOfStampsAlreadyThere = numberOfStampsAlreadyThere;
    }

    @Override
    public String toString() {
        return String.format("Card #%d, stamps: %d/%d",
                id, numberOfStampsNeeded, numberOfStampsAlreadyThere);
    }
}
