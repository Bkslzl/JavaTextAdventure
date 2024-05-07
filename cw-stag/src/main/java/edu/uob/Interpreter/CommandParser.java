package edu.uob.Interpreter;

import edu.uob.GameOperations.GameAction;
import edu.uob.Players;
import edu.uob.Tools.GameLoading;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class CommandParser {
    public static void handleCommand(String originalCommand){
        String[] findPlayerName = originalCommand.split(":");
        String currentPlayerName = findPlayerName[0];
        boolean isThereCurrentPlayer = false;
        for(Players currentPlayer : Players.playersList){
            if(currentPlayerName.equalsIgnoreCase(currentPlayer.name)){
                isThereCurrentPlayer = true;
                if(!handleBasicCommand(currentPlayer, originalCommand)){
                    System.out.println("Something wrong!");
                }
            }
        }
        if(!isThereCurrentPlayer){
            Players newPlayer = new Players(currentPlayerName, GameLoading.initialLocation);
            Players.playersList.add(newPlayer);
            if(!handleBasicCommand(newPlayer, originalCommand)){
                System.out.println("Something wrong!");
            }
        }

        /*if(!handleBasicCommand(Players.playersList.get(0), originalCommand)){
            System.out.println("Something wrong!");
        }*/
    }

    private static boolean handleBasicCommand(Players player, String originalCommand){

        //String[] tokens = originalCommand.split("\\s+");
        ArrayList<String> entitiesList = CommandChecker.findAllEntitiesAndStoreInTheList(originalCommand);

        ArrayList<String> actionKeyWords = CommandChecker.checkActionValidationAndFindTheCurrentOne(originalCommand);
        if(actionKeyWords.isEmpty()){
            return false;
        }

        String actionKeyWord;
        if(actionKeyWords.size() > 1) {
            actionKeyWord = checkIfOnlyOneActionIsPossible(actionKeyWords, entitiesList, player);
            if(actionKeyWord == null){
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

    private static String checkIfOnlyOneActionIsPossible(ArrayList<String> actionCommands,
                                                          ArrayList<String> entitiesList,
                                                          Players player){
        String currentAction = null;
        int satisfactionNumber = 0;
        for(String singleAction : actionCommands){
            GameAction theAction = findTheGameAction(singleAction);

            //首先判断subjects关键词对不对
            boolean subjectsKeyWords = ActionInterpreter.checkIfThereIsAtLeastOneSubject(theAction, entitiesList);

            //检查是否有这些必须道具
            boolean subjects = ActionInterpreter.checkIfHaveAllSubjects(player, theAction, entitiesList);

            if(subjectsKeyWords && subjects){
                satisfactionNumber++;
                currentAction = singleAction;
            }
        }
        if(satisfactionNumber == 1){
            return currentAction;
        }
        System.out.println("There is more than one action possible - which one do you want to perform ?");
        return null;
    }
}
