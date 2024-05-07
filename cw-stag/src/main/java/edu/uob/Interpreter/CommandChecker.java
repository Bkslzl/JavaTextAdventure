package edu.uob.Interpreter;

import edu.uob.Entities.Artefacts;
import edu.uob.Entities.Characters;
import edu.uob.Entities.GameEntity;
import edu.uob.Entities.Location;
import edu.uob.GameOperations.GameAction;

import java.util.*;

public class CommandChecker {
    // 初始化有效命令集合
    private static final Set<String> VALID_ACTIONS = new HashSet<>(Set.of("inventory", "inv", "get", "drop", "goto", "look"));
    private static final Set<String> VALID_ENTITIES = new HashSet<>();

    static {
        // 从已存在的hashActions获取所有命令
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

    public static ArrayList<String> checkActionValidationAndFindTheCurrentOne(String originalCommand) {
        ArrayList<String> actionCommands = new ArrayList<>();
        for (String command : VALID_ACTIONS) {
            if (originalCommand.toLowerCase().contains(command.toLowerCase())) {
                actionCommands.add(command);
            }
        }
        return actionCommands; // 返回找到的命令，如果没有找到则为 null
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

    public static void main(String[] args) {
        String input1 = "I want to get up item";
        System.out.println("Command found: " + checkActionValidationAndFindTheCurrentOne(input1));

        String input2 = "pick up item and use key";
        System.out.println("Command found: " + checkActionValidationAndFindTheCurrentOne(input2));

        String input3 = "just walking around";
        System.out.println("Command found: " + checkActionValidationAndFindTheCurrentOne(input3));
    }
}