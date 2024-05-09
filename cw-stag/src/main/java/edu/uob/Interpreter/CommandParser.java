package edu.uob.Interpreter;

import edu.uob.GameOperations.GameAction;
import edu.uob.GameOperations.Health;
import edu.uob.Players;
import edu.uob.Tools.GameLoading;

import java.util.ArrayList;

public class CommandParser {
    public static void handleCommand(String originalCommand){
        try {
            String[] findPlayerName = originalCommand.split(":");
            String currentPlayerName = findPlayerName[0];
            boolean isThereCurrentPlayer = false;
            for (Players currentPlayer : Players.playersList) {
                if (currentPlayerName.equalsIgnoreCase(currentPlayer.name)) {
                    isThereCurrentPlayer = true;
                    if (!handleBasicCommand(currentPlayer, originalCommand)) {
                        System.out.println("Grammar wrong!");
                    }
                }
            }
            if (!isThereCurrentPlayer) {
                Players newPlayer = new Players(currentPlayerName, GameLoading.initialLocation);
                Players.playersList.add(newPlayer);
                if (!handleBasicCommand(newPlayer, originalCommand)) {
                    System.out.println("Grammar error!");
                }
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            // 捕获数组越界异常并进行处理
            System.out.println("Command/original file error.");
            System.out.println("Array index out of bounds: " + e.getMessage());
        }catch (Exception e) {
            // 捕获所有其他异常
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static boolean handleBasicCommand(Players player, String originalCommand){
        String originalLowerCaseCommand = originalCommand.toLowerCase();

        ArrayList<String> entitiesList = CommandChecker.findAllEntitiesAndStoreInTheList(originalLowerCaseCommand);

        ArrayList<String> actionKeyWords = CommandChecker.checkActionValidationAndFindTheCurrentOne(originalLowerCaseCommand);
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

        System.out.println(actionKeyWord);

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

        return true;
    }
}
