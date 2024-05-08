package edu.uob.Interpreter;

import edu.uob.Entities.Artefacts;
import edu.uob.Entities.Characters;
import edu.uob.Entities.Furniture;
import edu.uob.Entities.Location;
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

        //检查是否有冗余subject
        if(ifCommandHasExtraEntities(entitiesList, theAction)){
            System.out.println("You give too many entities.");
            return false;
        }

        //检查生成的道具是否在别人背包里
        if(checkIfOtherPlayerHasTheProducedItem(theAction, player)){
            System.out.println("Other player has the produced item, so you cannot do that.");
            return false;
        }

        //可以执行了
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

    private static boolean ifCommandHasExtraEntities(ArrayList<String> entitiesList, GameAction theAction){
        // 将 neededEntities 转换为 HashSet 以提高查找效率
        HashSet<String> neededSet = new HashSet<>(theAction.neededEntities);

        // 检查 entitiesList 中的每个元素是否都在 neededSet 中
        for (String entity : entitiesList) {
            if (!neededSet.contains(entity)) {
                return true;  // 发现冗余元素
            }
        }
        return false;  // 所有元素都是必需的
    }
}
