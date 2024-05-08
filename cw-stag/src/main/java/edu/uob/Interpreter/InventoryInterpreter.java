package edu.uob.Interpreter;

import edu.uob.GameOperations.Inventory;
import edu.uob.Players;

import java.util.ArrayList;

public class InventoryInterpreter {
    public static boolean handleCommandInventory(Players player, ArrayList<String> entitiesList){
        if(!entitiesList.isEmpty()){
            System.out.println("Can not understand the purpose of the giving entities.");
            return false;
        }
        Inventory.printThePlayerInventory(player);
        return true;
    }
}
