package edu.uob;

import java.util.ArrayList;

public class Players {
    public static ArrayList<Players> playersList = new ArrayList<>();
    public String name;
    public String currentLocation;
    public ArrayList<String> inventory = new ArrayList<>();

    public Players(String name, String locationCreated){
        this.name = name;
        this.currentLocation = locationCreated;
    }

    public void pickUpItem(String item){
        this.inventory.add(item);
    }

}
