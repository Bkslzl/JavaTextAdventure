package edu.uob.Interpreter;

import edu.uob.Entities.*;
import edu.uob.GameOperations.GameAction;
import edu.uob.Players;

import java.util.ArrayList;
import java.util.HashSet;

public class ActionInterpreter {
    public static boolean handleCommandActions(GameAction theAction, Players player, ArrayList<String> entitiesList){
        if(entitiesList.isEmpty()){
            System.out.println("Please give more information.");
            return false;
        }

        //First, determine whether the subjects keywords are correct or not.
        if(!checkIfThereIsAtLeastOneSubject(theAction, entitiesList)){
            System.out.println("None of the required entities are mentioned.");
            return false;
        }

        //Check if you have these necessary items
        if(!checkIfHaveAllSubjects(player, theAction, entitiesList)){
            System.out.println("You do not have enough items.");
            return false;
        }

        //Check if there are redundant subjects
        if(ifCommandHasExtraEntities(entitiesList, theAction)){
            System.out.println("You give too many entities.");
            return false;
        }

        //Check whether the generated items are in someone else's backpack
        if(checkIfOtherPlayerHasTheProducedItem(theAction, player)){
            System.out.println("Other player has the produced item, so you cannot do that.");
            return false;
        }

        //Ready to go
        theAction.action(player);
        return true;
    }

    private static boolean checkIfOtherPlayerHasTheProducedItem(GameAction theAction, Players player){
        for(String producedItem : theAction.producedEntities){
            if(checkIfOtherPlayersHaveTheItem(player, producedItem)){
                return true;
            }
        }
        return false;
    }

    private static boolean checkIfOtherPlayersHaveTheItem(Players player, String item){
        for(Players currentPlayer : Players.playersList){
            if(!currentPlayer.name.equalsIgnoreCase(player.name)){
                for(String invItem : currentPlayer.inventory){
                    if(invItem.equalsIgnoreCase(item)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkIfThereIsAtLeastOneSubject(GameAction theAction, ArrayList<String> entitiesList){
        for (String item : theAction.neededEntities) {
            if (entitiesList.contains(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfHaveAllSubjects(Players player, GameAction theAction, ArrayList<String> entitiesList){
        //First look for it on the map and in the inventory
        for(String neededItem : theAction.neededEntities) {
            if (!(checkIfTheItemInTheInventory(neededItem, player) ||
                    checkIfTheItemInTheLocation(neededItem, player.currentLocation) ||
                    checkIfTheItemIsLocation(neededItem, player))){
                return false;
            }
        }
        return true;
    }

    private static boolean checkIfTheItemInTheLocation(String item, String location){
        return findItemWithNameAndLocation(item, location, Location.artefactsList) ||
                findItemWithNameAndLocation(item, location, Location.characterList) ||
                findItemWithNameAndLocation(item, location, Location.furnitureList) ||
                findItemWithNameAndLocation(item, location, Location.locationList);
    }

    private static boolean findItemWithNameAndLocation(String item, String location, ArrayList<? extends GameEntity> itemsList){
        for (GameEntity theItem : itemsList) {
            if (theItem.getName().equalsIgnoreCase(item) && theItem.getLocation().equalsIgnoreCase(location)) {
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

    private static boolean checkIfTheItemIsLocation(String item, Players player){
        //Check if the needed entity is a location
        return player.currentLocation.equalsIgnoreCase(item);
    }

    private static boolean ifCommandHasExtraEntities(ArrayList<String> entitiesList, GameAction theAction){
        HashSet<String> neededSet = new HashSet<>(theAction.neededEntities);
        //Check if each element in entitiesList is in neededSet
        for (String entity : entitiesList) {
            if (!neededSet.contains(entity)) {
                return true;  //Find redundant element
            }
        }
        return false;  //All elements are required
    }
}
