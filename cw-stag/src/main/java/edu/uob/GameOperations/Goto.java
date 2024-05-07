package edu.uob.GameOperations;

import edu.uob.Entities.Location;
import edu.uob.Players;

public class Goto {
    public static void gotoTheLocation(Players player, String destination){
        for(String map : Location.theMap){
            String[] twoLocations =map.split("\\s+");
            String start = twoLocations[0];
            String end = twoLocations[1];
            if(end.equalsIgnoreCase(destination) && start.equalsIgnoreCase(player.currentLocation)){
                player.currentLocation = destination;
                System.out.println("Successfully moved to the destination.");
                return;
            }
        }
        System.out.println("Can not move because of no path.");
    }
}
