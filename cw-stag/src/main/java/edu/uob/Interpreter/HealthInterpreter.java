package edu.uob.Interpreter;

import edu.uob.GameOperations.Health;
import edu.uob.GameOperations.Look;
import edu.uob.Players;

import java.util.ArrayList;

public class HealthInterpreter {
    public static boolean handleCommandHealth(Players player, ArrayList<String> entitiesList){
        if(!entitiesList.isEmpty()){
            System.out.println("Can not understand the purpose of giving entity.");
            return false;
        }
        Health.printPlayerCurrentHealth(player);
        return true;
    }
}
