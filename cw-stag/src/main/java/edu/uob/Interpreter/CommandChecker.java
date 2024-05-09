package edu.uob.Interpreter;

import edu.uob.Entities.Artefacts;
import edu.uob.Entities.Characters;
import edu.uob.Entities.GameEntity;
import edu.uob.Entities.Location;
import edu.uob.GameOperations.GameAction;
import edu.uob.Players;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandChecker {
    //Initialize the sets of valid commands
    public static Set<String> VALID_ACTIONS = new HashSet<>();
    public static Set<String> VALID_ENTITIES = new HashSet<>();

    public static void initializeGameData() {
        //Initialize VALID_ACTIONS
        VALID_ACTIONS.addAll(Set.of("inventory", "inv", "get", "drop", "goto", "look", "health"));
        VALID_ACTIONS.addAll(GameAction.hashActions.keySet());

        //Get all commands from existing hashActions
        VALID_ACTIONS.addAll(GameAction.hashActions.keySet());

        for(GameEntity entity : Location.artefactsList){
            VALID_ENTITIES.add(entity.getName());
        }
        for(GameEntity entity : Location.furnitureList){
            VALID_ENTITIES.add(entity.getName());
        }
        for(GameEntity entity : Location.locationList){
            VALID_ENTITIES.add(entity.getName());
        }
        for(Characters entity : Location.characterList){
            VALID_ENTITIES.add(entity.getName());
        }
    }

    public static ArrayList<String> checkActionValidationAndAdd(String originalCommand) {
        ArrayList<String> actionCommands = new ArrayList<>();
        for (String command : VALID_ACTIONS) {
            //Construct regular expressions to ensure correct matching of action keywords
            Pattern pattern = Pattern.compile("(?<!\\S)" + Pattern.quote(command) + "(?!\\S)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(originalCommand);

            if (matcher.find()) {
                actionCommands.add(command);
            }
        }
        return actionCommands; //Return the command found, or null if not found
    }

    public static ArrayList<String> findAllEntitiesAndStoreInTheList(String originalCommand) {
        String[] tokens = originalCommand.split("\\s+");
        ArrayList<String> entitiesInTheCommand = new ArrayList<>();
        for (String entity : VALID_ENTITIES) {
            for(String token : tokens) {
                if(token.equalsIgnoreCase(entity)){
                    entitiesInTheCommand.add(token);
                }
            }
        }
        return entitiesInTheCommand;
    }

    public static GameAction findTheGameAction(String actionKeyWord){
        HashSet<GameAction> retrievedActions = GameAction.hashActions.get(actionKeyWord);
        if (retrievedActions != null && !retrievedActions.isEmpty()) {
            Iterator<GameAction> iterator = retrievedActions.iterator();
            return iterator.next();//Get the first element
        } else {
            System.out.println("No actions available for the keyword: " + actionKeyWord);
            return null;
        }
    }

    public static String checkIfOnlyOneActionIsPossible(ArrayList<String> actionCommands,
                                                         ArrayList<String> entitiesList,
                                                         Players player){
        ArrayList<String> validActions = new ArrayList<>();
        String currentAction = null;

        for(String singleAction : actionCommands){

            //First determine whether there is a basic action that meets the needs
            int originalSize = validActions.size();
            if(checkIfOneBasicActionsValid(singleAction, validActions, player,entitiesList)){
                if(originalSize != validActions.size()) {
                    currentAction = singleAction;
                }
                continue;
            }

            //Then judge the advanced actions
            GameAction theAction = findTheGameAction(singleAction);
            if(theAction == null){
                return null;
            }

            //Determine whether the subjects keywords are correct
            boolean subjectsKeyWords = ActionInterpreter.checkIfThereIsAtLeastOneSubject(theAction, entitiesList);
            //Check if player has these necessary items
            boolean subjects = ActionInterpreter.checkIfHaveAllSubjects(player, theAction, entitiesList);
            //All are eligible to be added to the valid actionList
            if(subjectsKeyWords && subjects){
                validActions.add(singleAction);
                currentAction = singleAction;
            }
        }

        //Find valid actions number
        int validActionsNumber = countUniqueGameActionsNumber(validActions);
        if(validActionsNumber == 1){
            return currentAction;
        }
        System.out.println("There is more than one action possible - which one do you want to perform ?");
        return null;
    }

    public static int countUniqueGameActionsNumber(ArrayList<String> actionCommands) {
        Set<GameAction> uniqueActions = new HashSet<>();
        int basicActionsNumber = 0;
        for (String singleAction : actionCommands) {
            String action = singleAction.toLowerCase();
            if (singleAction.equalsIgnoreCase("inventory") ||
                    singleAction.equalsIgnoreCase("inv") ||
                    singleAction.equalsIgnoreCase("look") ||
                    singleAction.equalsIgnoreCase("get") ||
                    singleAction.equalsIgnoreCase("drop") ||
                    singleAction.equalsIgnoreCase("health") ||
                    singleAction.equalsIgnoreCase("goto")) {
                basicActionsNumber++;
                continue;
            }
            Iterator<GameAction> it = GameAction.hashActions.get(action).iterator();
            GameAction theAction = it.next();
            if (theAction != null) {
                uniqueActions.add(theAction);
            }
        }
        return uniqueActions.size() + basicActionsNumber; //Return the number of different GameAction objects
    }

    private static boolean checkIfOneBasicActionsValid(String singleAction,
                                                       ArrayList<String> validActions,
                                                       Players player,
                                                       ArrayList<String> entitiesList){
        boolean control = false;
        switch (singleAction) {
            case "inventory", "inv", "look", "health":
                validActions.add(singleAction);
                control = true;
                break;
            case "get":
                if(checkIfEntitiesSatisfyGet(player, entitiesList)){
                    validActions.add(singleAction);
                }
                control = true;
                break;

            case "drop":
                if(checkIfEntitiesSatisfyDrop(player, entitiesList)){
                    validActions.add(singleAction);
                }
                control = true;
                break;

            case "goto":
                if(checkIfEntitiesSatisfyGoto(player, entitiesList)){
                    validActions.add(singleAction);
                }
                control = true;
                break;
        }
        return control;
    }

    private static boolean checkIfEntitiesSatisfyGet(Players player, ArrayList<String> entitiesList){
        int entitiesNumber = 0;
        for(String currentEntity : entitiesList){
            for(Artefacts artefact : Location.artefactsList){
                if(currentEntity.equalsIgnoreCase(artefact.getName()) &&
                        player.currentLocation.equalsIgnoreCase(artefact.getLocation())){
                    entitiesNumber++;
                }
            }
        }
        return entitiesNumber == 1;
    }

    private static boolean checkIfEntitiesSatisfyDrop(Players player, ArrayList<String> entitiesList){
        int entitiesNumber = 0;
        for(String invItem : player.inventory){//In Inventory
            for(String entity : entitiesList) {//Mentioned by keywords
                if (invItem.equalsIgnoreCase(entity)){
                    entitiesNumber++;
                }
            }
        }
        return entitiesNumber == 1;
    }

    private static boolean checkIfEntitiesSatisfyGoto(Players player, ArrayList<String> entitiesList){
        int entitiesNumber = 0;
        for(String currentEntity : entitiesList){//Mentioned by keywords
            for(Location location : Location.locationList){//Need to be location
                if(currentEntity.equalsIgnoreCase(location.getName()) &&//Need be a path
                        checkIfThereIsAPath(player, currentEntity)){
                    entitiesNumber++;
                }
            }
        }
        return entitiesNumber == 1;
    }

    private static boolean checkIfThereIsAPath(Players player, String destination){
        for(String map : Location.theMap){
            String[] twoLocations =map.split("\\s+");
            String start = twoLocations[0];
            String end = twoLocations[1];
            if(end.equalsIgnoreCase(destination) && start.equalsIgnoreCase(player.currentLocation)){
                return true;//There is a path
            }
        }
        return false;
    }
}