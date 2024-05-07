package edu.uob.GameOperations;

import edu.uob.Entities.Artefacts;
import edu.uob.Entities.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class GameAction {
    public static HashMap<String,HashSet<GameAction>> hashActions = new HashMap<String, HashSet<GameAction>>();
    public ArrayList<String> consumedEntities = new ArrayList<>();
    public ArrayList<String> neededEntities = new ArrayList<>();
    public ArrayList<String> producedEntities = new ArrayList<>();
    public String narration;

    public GameAction(String narration,
                      ArrayList<String> consumedEntities,
                      ArrayList<String> neededEntities,
                      ArrayList<String> producedEntities){
        this.narration = narration;
        this.consumedEntities = consumedEntities;
        this.producedEntities = producedEntities;
        this.neededEntities = neededEntities;
    }

    public void action(){
        //consumption
        for (String currentItem : consumedEntities) {
            Iterator<Artefacts> iterator = Location.artefactsList.iterator();
            while (iterator.hasNext()) {
                Artefacts item = iterator.next();
                if (item.getName().equalsIgnoreCase(currentItem)) {
                    iterator.remove();
                }
            }
        }

        //needed还不知道
    }

}
