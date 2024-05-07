package edu.uob.GameOperations;

import edu.uob.Entities.Artefacts;
import edu.uob.Entities.Characters;
import edu.uob.Entities.Furniture;
import edu.uob.Entities.Location;
import edu.uob.Players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class GameAction {
    public static HashMap<String,HashSet<GameAction>> hashActions = new HashMap<String, HashSet<GameAction>>();
    public ArrayList<String> consumedEntities = new ArrayList<>();
    public ArrayList<String> neededEntities = new ArrayList<>();
    public ArrayList<String> producedEntities = new ArrayList<>();
    public String narration;

    public GameAction(String narration,
                      ArrayList<String> consumedEntities,
                      ArrayList<String> neededEntities,
                      ArrayList<String> producedEntities){
        this.narration = narration;
        this.consumedEntities = consumedEntities;
        this.producedEntities = producedEntities;
        this.neededEntities = neededEntities;
    }

    public void action(Players player){
        //两步，1消耗，2产生
        for (String consumedItem : consumedEntities) {
            findTheItemAndConsumeIt(consumedItem, player);
        }

        for(String producedItem : producedEntities){
            produceTheItemInTheLocation(producedItem, player);
        }
        System.out.println(narration);
    }

    private static void findTheItemAndConsumeIt(String item, Players player){
        for (Artefacts artefact : Location.artefactsList) {
            if (artefact.getName().equalsIgnoreCase(item)) {
                artefact.changeLocation("storeroom");
                System.out.println("Consume Item: " + item + ".");
                return;
            }
        }
        for (Characters character : Location.characterList) {
            if (character.getName().equalsIgnoreCase(item)) {
                character.changeLocation("storeroom");
                System.out.println("Consume Item: " + item + ".");
                return;
            }
        }
        for (Furniture furniture : Location.furnitureList) {
            if (furniture.getName().equalsIgnoreCase(item)) {
                furniture.changeLocation("storeroom");
                System.out.println("Consume Item: " + item + ".");
                return;
            }
        }
        for (Location theLocation : Location.locationList) {
            if (theLocation.getName().equalsIgnoreCase(item)) {
                //删除当前位置和消耗位置之间的路径（在其他游戏位置中可能还有通往该位置的其他路径)
                removePath(player.currentLocation, item);
                System.out.println("The path to " + item + " has been removed.");
                return;
            }
        }
    }

    public static void removePath(String start, String end) {
        Iterator<String> iterator = Location.theMap.iterator();
        while (iterator.hasNext()) {
            String path = iterator.next();
            String[] thePath = path.split("\\s+");
            // 检查路径起点和终点是否匹配
            if (thePath[0].equalsIgnoreCase(start) && thePath[1].equalsIgnoreCase(end)) {
                iterator.remove();
                break;
            }
        }
    }

    private static void produceTheItemInTheLocation(String item, Players player) {
        for (Artefacts artefact : Location.artefactsList) {
            if (artefact.getName().equalsIgnoreCase(item)) {
                artefact.changeLocation(player.currentLocation);
                System.out.println("Produce the item: " + item + ".");
                return;
            }
        }
        for (Characters character : Location.characterList) {
            if (character.getName().equalsIgnoreCase(item)) {
                character.changeLocation(player.currentLocation);
                System.out.println("Produce the item: " + item + ".");
                return;
            }
        }
        for (Furniture furniture : Location.furnitureList) {
            if (furniture.getName().equalsIgnoreCase(item)) {
                furniture.changeLocation(player.currentLocation);
                System.out.println("Produce the item: " + item + ".");
                return;
            }
        }
        for (Location theLocation : Location.locationList) {
            if (theLocation.getName().equalsIgnoreCase(item)) {
                //删除当前位置和消耗位置之间的路径（在其他游戏位置中可能还有通往该位置的其他路径)
                Location.theMap.add(player.currentLocation + " " + item);
                System.out.println("The path to " + item + " has occurred.");
                return;
            }
        }
    }

}
