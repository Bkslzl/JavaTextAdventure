package edu.uob.Interpreter;

import edu.uob.GameOperations.GameAction;
import edu.uob.Players;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class CommandParser {
    public static void handleCommand(String originalCommand){
        if(!handleBasicCommand(Players.playersList.get(0), originalCommand)){
            System.out.println("Something wrong!");
        }
    }

    private static boolean handleBasicCommand(Players player, String originalCommand){
        //String[] tokens = originalCommand.split("\\s+");
        String actionKeyWord = CommandChecker.checkActionValidationAndFindTheCurrentOne(originalCommand);
        System.out.println(actionKeyWord);
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

        else if(findTheGameAction(actionKeyWord) != null){
            GameAction theAction = findTheGameAction(actionKeyWord);
            return ActionInterpreter.handleCommandActions(theAction, player, entitiesList);
        }

        return true;
    }

    private static GameAction findTheGameAction(String actionKeyWord){
        HashSet<GameAction> retrievedActions = GameAction.hashActions.get(actionKeyWord);
        if (retrievedActions != null && !retrievedActions.isEmpty()) {
            Iterator<GameAction> iterator = retrievedActions.iterator();
            return iterator.next();  // 获取第一个元素
        } else {
            System.out.println("No actions available for the keyword: " + actionKeyWord);
            return null;
        }
    }

}
