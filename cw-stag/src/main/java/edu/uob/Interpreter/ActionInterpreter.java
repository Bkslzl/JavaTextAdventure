package edu.uob.Interpreter;

import edu.uob.Entities.Artefacts;
import edu.uob.Entities.Characters;
import edu.uob.Entities.Furniture;
import edu.uob.Entities.Location;
import edu.uob.GameOperations.GameAction;
import edu.uob.Players;

import java.util.ArrayList;

public class ActionInterpreter {
    public static boolean handleCommandActions(GameAction theAction, Players player, ArrayList<String> entitiesList){
        if(entitiesList.isEmpty()){
            System.out.println("Please give more information.");
            return false;
        }

        //首先判断subjects关键词对不对
        if(!checkIfThereIsAtLeastOneSubject(theAction, entitiesList)){
            System.out.println("None of the required entities are mentioned.");
            return false;
        }

        //检查是否有这些必须道具
        if(!checkIfHaveAllSubjects(player, theAction, entitiesList)){
            System.out.println("You do not have enough items.");
            return false;
        }

        //可以执行了
        theAction.action(player);
        return true;
    }

    private static boolean checkIfThereIsAtLeastOneSubject(GameAction theAction, ArrayList<String> entitiesList){
        for (String item : theAction.neededEntities) {
            if (entitiesList.contains(item)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkIfHaveAllSubjects(Players player, GameAction theAction, ArrayList<String> entitiesList){
        //先去地图上找,和背包里
        for(String neededItem : theAction.neededEntities) {
            if (!(checkIfTheItemInTheInventory(neededItem, player) ||
                    checkIfTheItemInTheLocation(neededItem, player.currentLocation))){
                return false;
            }
        }
        return true;
    }

    private static boolean checkIfTheItemInTheLocation(String item, String location){
        for (Artefacts artefact : Location.artefactsList) {
            if (artefact.getName().equalsIgnoreCase(item) && artefact.getLocation().equalsIgnoreCase(location)) {
                return true;
            }
        }
        for (Characters character : Location.characterList) {
            if (character.getName().equalsIgnoreCase(item) && character.getLocation().equalsIgnoreCase(location)) {
                return true;
            }
        }
        for (Furniture furniture : Location.furnitureList) {
            if (furniture.getName().equalsIgnoreCase(item) && furniture.getLocation().equalsIgnoreCase(location)) {
                return true;
            }
        }
        for (Location theLocation : Location.locationList) {
            if (theLocation.getName().equalsIgnoreCase(item) && theLocation.getLocation().equalsIgnoreCase(location)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkIfTheItemInTheInventory(String item, Players player){
        for(String inventoryItem : player.inventory){
            if(inventoryItem.equalsIgnoreCase(item)){
                return true;
            }
        }
        return false;
    }
}
