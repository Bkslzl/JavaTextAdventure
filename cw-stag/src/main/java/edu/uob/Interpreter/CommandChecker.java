package edu.uob.Interpreter;

import edu.uob.Entities.Characters;
import edu.uob.Entities.GameEntity;
import edu.uob.Entities.Location;
import edu.uob.GameOperations.GameAction;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandChecker {
    // 初始化有效命令集合
    public static Set<String> VALID_ACTIONS = new HashSet<>(/*Set.of("inventory", "inv", "get", "drop", "goto", "look")*/);
    public static Set<String> VALID_ENTITIES = new HashSet<>();

    /*static {
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
    }*/

    // 初始化方法
    public static void initializeGameData() {
        // 初始化 VALID_ACTIONS
        VALID_ACTIONS.addAll(Set.of("inventory", "inv", "get", "drop", "goto", "look"));
        VALID_ACTIONS.addAll(GameAction.hashActions.keySet());

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
            /*if (originalCommand.toLowerCase().contains(command.toLowerCase())) {
                actionCommands.add(command);
            }*/

            // 构建正则表达式，使用\b来标记单词的边界
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(command) + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(originalCommand);

            if (matcher.find()) {
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
}