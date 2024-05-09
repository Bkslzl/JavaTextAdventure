package edu.uob.GameOperations;

import edu.uob.Entities.Artefacts;
import edu.uob.Entities.Characters;
import edu.uob.Entities.Furniture;
import edu.uob.Entities.Location;
import edu.uob.Players;
import edu.uob.Tools.GameLoading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

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
        //Two steps, 1 consumption, 2 production
        for (String consumedItem : consumedEntities) {
            findTheItemAndConsumeIt(consumedItem, player);
        }

        for(String producedItem : producedEntities){
            produceTheItemInTheLocation(producedItem, player);
        }
        System.out.println(narration);
    }

    private static void findTheItemAndConsumeIt(String item, Players player){
        if(item.equalsIgnoreCase("health")){
            player.health -= 1;
            if(player.health <= 0){
                playerDead(player);
            }
            return;
        }

        removeSpecificItemInInventory(player, item);

        for (Artefacts artefact : Location.artefactsList) {
            if (artefact.getName().equalsIgnoreCase(item)) {
                artefact.changeLocation("storeroom");
                artefact.owner = null;
                System.out.println("Consume artefact: " + item + ".");
                return;
            }
        }
        for (Characters character : Location.characterList) {
            if (character.getName().equalsIgnoreCase(item)) {
                character.changeLocation("storeroom");
                System.out.println("Consume character: " + item + ".");
                return;
            }
        }
        for (Furniture furniture : Location.furnitureList) {
            if (furniture.getName().equalsIgnoreCase(item)) {
                furniture.changeLocation("storeroom");
                System.out.println("Consume furniture: " + item + ".");
                return;
            }
        }
        for (Location theLocation : Location.locationList) {
            if (theLocation.getName().equalsIgnoreCase(item)) {
                //Delete the path between the current location and the consumed location
                //(there may be other paths to this location in other game locations)
                if(theLocation.getName().equalsIgnoreCase(player.currentLocation)){
                    System.out.println("Cannot delete current location.");
                    return;
                }
                removePath(player.currentLocation, item);
                System.out.println("The path form " + player.currentLocation + " to " + item + " has been removed.");
                return;
            }
        }
    }

    private static void removeSpecificItemInInventory(Players player, String item){
        Iterator<String> iterator = player.inventory.iterator();
        while (iterator.hasNext()) {
            String inventoryItem = iterator.next();
            if (inventoryItem.equalsIgnoreCase(item)) {
                iterator.remove();
                return;
            }
        }
    }

    private static void removePath(String start, String end) {
        Iterator<String> iterator = Location.theMap.iterator();
        while (iterator.hasNext()) {
            String path = iterator.next();
            String[] thePath = path.split("\\s+");
            //Check if the path start and end points match
            if (thePath[0].equalsIgnoreCase(start) && thePath[1].equalsIgnoreCase(end)) {
                iterator.remove();
                break;
            }
        }
    }

    private static void produceTheItemInTheLocation(String item, Players player) {
        if(item.equalsIgnoreCase("health")){
            if(player.health < 3){
                player.health++;
            }
            System.out.println("You gain health.");
            return;
        }

        for (Artefacts artefact : Location.artefactsList) {
            if (artefact.getName().equalsIgnoreCase(item)) {
                artefact.changeLocation(player.currentLocation);
                System.out.println("Produce the artefact: " + item + ".");
                return;
            }
        }
        for (Characters character : Location.characterList) {
            if (character.getName().equalsIgnoreCase(item)) {
                character.changeLocation(player.currentLocation);
                System.out.println("Produce the character: " + item + ".");
                return;
            }
        }
        for (Furniture furniture : Location.furnitureList) {
            if (furniture.getName().equalsIgnoreCase(item)) {
                furniture.changeLocation(player.currentLocation);
                System.out.println("Produce the furniture: " + item + ".");
                return;
            }
        }
        for (Location theLocation : Location.locationList) {
            if (theLocation.getName().equalsIgnoreCase(item)) {
                //Delete the path between the current location and the consumed location
                //(there may be other paths to this location in other game locations)
                Location.theMap.add(player.currentLocation + " " + item);
                System.out.println("The path from " + player.currentLocation + " to " + item + " has occurred.");
                return;
            }
        }
    }

    private static void playerDead(Players player){
        Iterator<String> inventoryIterator = player.inventory.iterator();
        while (inventoryIterator.hasNext()) {
            String droppedItem = inventoryIterator.next();
            for (Artefacts artefact : Location.artefactsList) {
                if (artefact.getName().equalsIgnoreCase(droppedItem)) {
                    artefact.changeLocation(player.currentLocation);  //Change item location
                    artefact.owner = null;  //Clear item owner
                    System.out.println("Because of death, you drop the item: " + droppedItem + ".");
                    inventoryIterator.remove();//Delete the item
                }
            }
        }
        player.health = 3;
        player.currentLocation = GameLoading.initialLocation;
        System.out.println("You died and lost all of your items, you must return to the start of the game.");
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameAction that = (GameAction) o;
        return Objects.equals(narration, that.narration) &&
                Objects.equals(consumedEntities, that.consumedEntities) &&
                Objects.equals(neededEntities, that.neededEntities) &&
                Objects.equals(producedEntities, that.producedEntities);
    }

    public int hashCode() {
        return Objects.hash(narration, consumedEntities, neededEntities, producedEntities);
    }
}
