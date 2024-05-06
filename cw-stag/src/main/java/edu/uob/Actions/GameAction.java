package edu.uob.Actions;

import edu.uob.Entities.Artefacts;
import edu.uob.Entities.Location;
import edu.uob.Entities.NewEntities;
import edu.uob.GameClient;
import edu.uob.GameEntity;

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

        //produce
        for(String currentItem : producedEntities) {
            NewEntities newItem = new NewEntities(currentItem, "");
            NewEntities.newEntitiesList.add(newItem);
        }

        //needed还不知道
    }

}
