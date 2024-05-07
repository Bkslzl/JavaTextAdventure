package edu.uob.Interpreter;

import edu.uob.GameOperations.Look;
import edu.uob.Players;

import java.util.ArrayList;

public class LookInterpreter {
    public static boolean handleCommandLook(Players player, ArrayList<String> entitiesList){
        if(!entitiesList.isEmpty()){
            System.out.println("Can not understand the purpose of giving entity.");
            return false;
        }
        Look.printAllThingsInCurrentLocation(player);
        return true;
    }
}
