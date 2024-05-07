package edu.uob.Interpreter;

import edu.uob.Players;

import java.util.ArrayList;

public class CommandParser {
    public static void handleCommand(String originalCommand){
        if(!handleBasicCommand(Players.playersList.get(0), originalCommand)){
            System.out.println("Something wrong!");
        }
    }

    private static boolean handleBasicCommand(Players player, String originalCommand){
        //String[] tokens = originalCommand.split("\\s+");
        String actionKeyWord = CommandChecker.checkActionValidationAndFindTheCurrentOne(originalCommand);
        if(actionKeyWord == null){
            return false;
        }
        ArrayList<String> entitiesList = CommandChecker.findAllEntitiesAndStoreInTheList(originalCommand);
        if(actionKeyWord.equalsIgnoreCase("goto")){
            return GotoInterpreter.handleCommandGoto(player, entitiesList);
        }
        else if(actionKeyWord.equalsIgnoreCase("inventory") ||
                actionKeyWord.equalsIgnoreCase("inv")){
            return InventoryInterpreter.handleCommandInventory(player, entitiesList);
        }
        else if(actionKeyWord.equalsIgnoreCase("get")){
            return GetInterpreter.handleCommandGet(player, entitiesList);
        }
        else if(actionKeyWord.equalsIgnoreCase("drop")){
            return DropInterpreter.handleCommandDrop(player, entitiesList);
        }
        else if(actionKeyWord.equalsIgnoreCase("look")){
            return LookInterpreter.handleCommandLook(player, entitiesList);
        }

        return true;
    }

}
