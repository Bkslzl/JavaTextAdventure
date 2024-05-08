package edu.uob.GameOperations;

import edu.uob.Entities.Artefacts;
import edu.uob.Entities.GameEntity;
import edu.uob.Entities.Location;
import edu.uob.Players;
import edu.uob.Tools.Findings;

public class Get {
    public static void pickUpSpecificItem(Players player, String item){
        for(Artefacts artefact : Location.artefactsList){
            if(artefact.getLocation().equalsIgnoreCase(player.currentLocation) && artefact.getName().equalsIgnoreCase(item)){
                player.inventory.add(artefact.getName());
                artefact.changeLocation("inventory");
                artefact.owner = player.name;
                System.out.println("Successfully picked up the item: " + item);

                GameEntity newEntity = Findings.accordingToNameFindItem(item);
                if(newEntity != null){
                    System.out.println("The description is: " + newEntity.getDescription());
                }

                return;
            }
        }
        System.out.println("Can not find the item you want to pick up in current location.");
    }
}
