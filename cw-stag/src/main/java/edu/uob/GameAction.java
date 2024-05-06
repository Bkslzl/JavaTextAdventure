package edu.uob;

import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import edu.uob.Entities.Artefacts;
import edu.uob.Entities.Characters;
import edu.uob.Entities.Furniture;
import edu.uob.Entities.Location;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;


public class GameAction
{
    public static boolean loadGameData(){
        try {
            Parser parser = new Parser();
            FileReader reader = new FileReader("cw-stag" + File.separator +
                    "config" + File.separator +
                    "extended-entities.dot");
            parser.parse(reader);

            Graph wholeDocument = parser.getGraphs().get(0);//找到第一个大图
            ArrayList<Graph> sections = wholeDocument.getSubgraphs();//储存所有的一级子图(一个）
            ArrayList<Graph> locations = sections.get(0).getSubgraphs();//第一个大图含有所有的地点信息
            for(Graph currentLocation : locations){
                Node currentLocationDetails = currentLocation.getNodes(false).get(0);
                String currentLocationName = currentLocationDetails.getId().getId();
                String currentLocationDescription = currentLocationDetails.getAttribute("description");
                Location newLocation = new Location(currentLocationName,currentLocationDescription);
                Location.locationList.add(newLocation);//储存所有的地点信息（名字描述）
                //System.out.println(currentLocationName+ "       "+currentLocationDescription);

                //获取id
                ArrayList<Graph> items = currentLocation.getSubgraphs();
                for(Graph currentItem : items){
                    String currentItemName = currentItem.getId().getId();
                    //处理不同种类的东西
                    handleSpecificItems(currentItemName, currentItem, currentLocationName);
                }
            }

            ArrayList<Edge> paths = sections.get(1).getEdges();//找到第二个大图
            for(Edge currentPath : paths) {
                Node fromLocation = currentPath.getSource().getNode();
                String fromName = fromLocation.getId().getId();
                Node toLocation = currentPath.getTarget().getNode();
                String toName = toLocation.getId().getId();
                Location.theMap.add(fromName + " " + toName);
                //System.out.println(fromName + "          " + toName);
            }

        } catch (FileNotFoundException | ParseException fife) {
            return false;
        }
        return true;
    }

    private static void handleSpecificItems(String itemId, Graph currentLocation, String currentLocationName){
        ArrayList<Node> currentItemsList = currentLocation.getNodes(false);
        if(itemId.equalsIgnoreCase("Artefacts")){
            for(Node currentNode : currentItemsList){
                String itemName = currentNode.getId().getId();
                String itemsDescription = currentNode.getAttribute("description");
                Artefacts newArtefacts = new Artefacts(itemName,itemsDescription,currentLocationName);
                Location.artefactsList.add(newArtefacts);
                //System.out.println("当前添加的物品信息："+itemName + "   " + itemsDescription + "  " + currentLocationName);
            }
        }else if(itemId.equalsIgnoreCase("Furniture")){
            for(Node currentNode : currentItemsList){
                String itemName = currentNode.getId().getId();
                String itemsDescription = currentNode.getAttribute("description");
                Furniture newFurniture = new Furniture(itemName,itemsDescription,currentLocationName);
                Location.furnitureList.add(newFurniture);
                //System.out.println("当前添加的物品信息："+itemName + "   " + itemsDescription + "  " + currentLocationName);
            }
        }else if(itemId.equalsIgnoreCase("Characters")){
            for(Node currentNode : currentItemsList){
                String itemName = currentNode.getId().getId();
                String itemsDescription = currentNode.getAttribute("description");
                Characters newCharacter = new Characters(itemName,itemsDescription,currentLocationName);
                Location.characterList.add(newCharacter);
                //System.out.println("当前添加的物品信息："+itemName + "   " + itemsDescription + "  " + currentLocationName);
            }
        }
    }

    public static void handleCommand(){

    }

    public static void main(String[] args){

        boolean a =loadGameData();
        System.out.println(a);
    }
}
