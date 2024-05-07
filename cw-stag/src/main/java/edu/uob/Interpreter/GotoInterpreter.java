package edu.uob.Interpreter;

import edu.uob.GameOperations.Goto;
import edu.uob.Players;

import java.util.ArrayList;

public class GotoInterpreter {
    public static boolean handleCommandGoto(Players player, ArrayList<String> entitiesList){
        if(entitiesList.size() != 1){
            System.out.println("Can not move to multiple destinations.");
            return false;
        }
        Goto.gotoTheLocation(player, entitiesList.get(0));
        return true;
    }
}
