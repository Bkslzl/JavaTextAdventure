package edu.uob.Interpreter;

import edu.uob.GameOperations.GameAction;
import edu.uob.Players;

import java.util.*;

public class CommandParser {
    public static void parserHandleCommand(String originalCommand){
        try {
            Players newPlayer = Players.createNewPlayerIfPossible(originalCommand);
            originalCommand = originalCommand.replaceAll("[,.!:?]", " ");
            if (!handleBasicCommand(newPlayer, originalCommand)) {
                System.out.println("Grammar error!");
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Command/original file error.");
            System.out.println("Array index out of bounds: " + e.getMessage());
        }catch (Exception e) {
            //Catch all other exceptions
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static boolean handleBasicCommand(Players player, String originalCommand){
        String originalLowerCaseCommand = originalCommand.toLowerCase();

        ArrayList<String> entitiesList = CommandChecker.findAllEntitiesAndStoreInTheList(originalLowerCaseCommand);
        Set<String> uniqueEntities = new LinkedHashSet<>(entitiesList);
        entitiesList = new ArrayList<>(uniqueEntities);

        ArrayList<String> actionKeyWords = CommandChecker.checkActionValidationAndAdd(originalLowerCaseCommand);
        mergeInvKeywords(actionKeyWords);

        if(actionKeyWords.isEmpty()){
            System.out.println("Can not find any actions.");
            return false;
        }

        String actionKeyWord;
        if(actionKeyWords.size() > 1) {
            actionKeyWord = CommandChecker.checkIfOnlyOneActionIsPossible(actionKeyWords, entitiesList, player);
            if(actionKeyWord == null){
                System.out.println("Can not find any available actions.");
                return false;
            }
        }else{
            actionKeyWord = actionKeyWords.get(0);
        }

        return commandInterpreter(actionKeyWord, player, entitiesList);
    }

    private static boolean commandInterpreter(String actionKeyWord, Players player, ArrayList<String> entitiesList){
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
        else if(actionKeyWord.equalsIgnoreCase("health")){
            return HealthInterpreter.handleCommandHealth(player, entitiesList);
        }

        else if(CommandChecker.findTheGameAction(actionKeyWord) != null){
            GameAction theAction = CommandChecker.findTheGameAction(actionKeyWord);
            return ActionInterpreter.handleCommandActions(theAction, player, entitiesList);
        }
        return false;
    }

    private static void mergeInvKeywords(ArrayList<String> actionKeyWords) {
        boolean containsInv = actionKeyWords.contains("inv");
        boolean containsInventory = actionKeyWords.contains("inventory");

        if (containsInv && containsInventory) {
            actionKeyWords.removeAll(List.of("inventory"));
        }
    }
}
