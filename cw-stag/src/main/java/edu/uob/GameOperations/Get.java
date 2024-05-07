package edu.uob.GameOperations;

import edu.uob.Entities.Artefacts;
import edu.uob.Entities.Location;
import edu.uob.Players;

import java.util.ArrayList;

public class Get {
    public static void pickUpSpecificItem(Players player, String item){
        for(Artefacts artefact : Location.artefactsList){
            if(artefact.getLocation().equalsIgnoreCase(player.currentLocation) && artefact.getName().equalsIgnoreCase(item)){
                player.inventory.add(artefact.getName());
                artefact.changeLocation("inventory");
                System.out.println("Successfully picked up the item: " + item);
                return;
            }
        }
        System.out.println("Can not find the item you want to pick in current location.");
    }
}
