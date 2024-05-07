package edu.uob.GameOperations;

import edu.uob.Entities.Artefacts;
import edu.uob.Entities.Location;
import edu.uob.Players;

public class Drop {
    public static void dropTheSpecificItem(Players player, String itemName){
        for(String findItem : player.inventory){
            if(findItem.equalsIgnoreCase(itemName)){
                dropTheItemInCurrentLocation(player,itemName);
                System.out.println("Successfully dropped the item: " + itemName);
                return;
            }
        }
        System.out.println("Can not find the artefact in your inventory.");
    }

    private static void dropTheItemInCurrentLocation(Players player, String itemName){
        for(Artefacts artefact : Location.artefactsList){
            if(artefact.getName().equalsIgnoreCase(itemName)){
                artefact.changeLocation(player.currentLocation);
                return;
            }
        }
    }
}
