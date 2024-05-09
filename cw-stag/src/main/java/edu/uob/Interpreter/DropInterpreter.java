package edu.uob.Interpreter;

import edu.uob.Entities.Characters;
import edu.uob.Entities.Furniture;
import edu.uob.Entities.Location;
import edu.uob.GameOperations.Drop;
import edu.uob.Players;

import java.util.ArrayList;

public class DropInterpreter {
    public static boolean handleCommandDrop(Players player, ArrayList<String> entitiesList){
        if(entitiesList.size() != 1){
            System.out.println("Can not find the item or drop more than one items at a time.");
            return false;
        }
        for(Characters character : Location.characterList){
            if(character.getName().equalsIgnoreCase(entitiesList.get(0))){
                System.out.println("Can not drop characters.");
                return false;
            }
        }
        for(Location location : Location.locationList){
            if(location.getName().equalsIgnoreCase(entitiesList.get(0))){
                System.out.println("Can not drop locations.");
                return false;
            }
        }
        for(Furniture furniture : Location.furnitureList){
            if(furniture.getName().equalsIgnoreCase(entitiesList.get(0))){
                System.out.println("Can not drop furniture.");
                return false;
            }
        }
        Drop.dropTheSpecificItem(player,entitiesList.get(0));
        return true;
    }
}
