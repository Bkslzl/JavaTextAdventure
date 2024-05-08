package edu.uob.GameOperations;

import edu.uob.Entities.Artefacts;
import edu.uob.Entities.GameEntity;
import edu.uob.Entities.Location;
import edu.uob.Players;
import edu.uob.Tools.Findings;

import java.util.Iterator;

public class Drop {
    public static void dropTheSpecificItem(Players player, String itemName){
        Iterator<String> iterator = player.inventory.iterator();
        while (iterator.hasNext()) {
            String findItem = iterator.next();
            if (findItem.equalsIgnoreCase(itemName)) {
                dropTheItemInCurrentLocation(player, itemName);
                iterator.remove();
                System.out.println("Successfully dropped the item: " + itemName);

                GameEntity newEntity = Findings.accordingToNameFindItem(itemName);
                if(newEntity != null){
                    System.out.println("The description is: " + newEntity.getDescription());
                }

                return;
            }
        }
        System.out.println("Can not find the artefact in your inventory.");
    }

    private static void dropTheItemInCurrentLocation(Players player, String itemName){
        for(Artefacts artefact : Location.artefactsList){
            if(artefact.getName().equalsIgnoreCase(itemName)){
                artefact.changeLocation(player.currentLocation);
                artefact.owner = null;
                return;
            }
        }
    }
}
