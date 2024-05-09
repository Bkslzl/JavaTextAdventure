package edu.uob;

import edu.uob.Tools.GameLoading;

import java.util.ArrayList;

public class Players {
    public static ArrayList<Players> playersList = new ArrayList<>();
    public String name;
    public String currentLocation;
    public ArrayList<String> inventory = new ArrayList<>();

    public int health;

    public Players(String name, String locationCreated){
        this.name = name;
        this.currentLocation = locationCreated;
        this.health = 3;
    }

    public static Players createNewPlayerIfPossible(String originalCommand){
        String[] findPlayerName = originalCommand.split(":");
        String currentPlayerName = findPlayerName[0];
        for (Players currentPlayer : Players.playersList) {
            if (currentPlayerName.equalsIgnoreCase(currentPlayer.name)) {
                return currentPlayer;
            }
        }
        Players newPlayer = new Players(currentPlayerName, GameLoading.initialLocation);
        Players.playersList.add(newPlayer);
        return newPlayer;
    }
}
