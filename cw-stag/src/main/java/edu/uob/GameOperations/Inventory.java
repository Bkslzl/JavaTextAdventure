package edu.uob.GameOperations;

import edu.uob.Entities.GameEntity;
import edu.uob.Players;
import edu.uob.Tools.Findings;

public class Inventory {
    public static void printThePlayerInventory(Players player){
        if(player.inventory.isEmpty()){
            System.out.println("There is nothing in the inventory.");
            return;
        }
        System.out.println("All artefacts you have are shown below:");
        for (String item : player.inventory) {
            System.out.println("Item: " + item);

            GameEntity newEntity = Findings.accordingToNameFindItem(item);
            if(newEntity != null){
                System.out.println("The description is: " + newEntity.getDescription());
            }
        }
    }
}
