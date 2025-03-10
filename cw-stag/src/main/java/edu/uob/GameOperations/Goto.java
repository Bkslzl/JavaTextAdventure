package edu.uob.GameOperations;

import edu.uob.Entities.GameEntity;
import edu.uob.Entities.Location;
import edu.uob.Players;
import edu.uob.Tools.Findings;

public class Goto {
    public static void gotoTheLocation(Players player, String destination){
        for(String map : Location.theMap){
            String[] twoLocations =map.split("\\s+");
            String start = twoLocations[0];
            String end = twoLocations[1];
            if(end.equalsIgnoreCase(destination) && start.equalsIgnoreCase(player.currentLocation)){
                player.currentLocation = destination;
                System.out.println("Go from " + start +" to " + destination);

                GameEntity newEntity = Findings.accordingToNameFindItem(destination);
                if(newEntity != null){
                    System.out.println("The description is: " + newEntity.getDescription());
                }

                return;
            }
        }
        System.out.println("Can not move because of no path or you give the wrong destination.");
    }
}
