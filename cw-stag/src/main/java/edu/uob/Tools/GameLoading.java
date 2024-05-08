package edu.uob.Tools;

import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import edu.uob.Entities.Artefacts;
import edu.uob.Entities.Characters;
import edu.uob.Entities.Furniture;
import edu.uob.Entities.Location;
import edu.uob.GameOperations.GameAction;
import edu.uob.Interpreter.CommandChecker;
import edu.uob.Interpreter.CommandParser;
import edu.uob.Players;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class GameLoading {
    public static String initialLocation;

    public static void loadGameData(File entitiesFile, File actionsFile){
        resetTheGameData();

        boolean entitiesLoading = GameLoading.loadEntitiesData(entitiesFile);
        boolean actionLoading = GameLoading.loadActionData(actionsFile);
        CommandChecker.initializeGameData();

        if(! (entitiesLoading || actionLoading)){
            System.out.println("loading data error");
        }
    }

    private static boolean loadEntitiesData(File entitiesFile){
        try {
            Parser parser = new Parser();
            //FileReader reader = new FileReader("config" + File.separator + "extended-entities.dot");
            FileReader reader = new FileReader(entitiesFile);
            parser.parse(reader);

            Graph wholeDocument = parser.getGraphs().get(0);//找到第一个大图
            ArrayList<Graph> sections = wholeDocument.getSubgraphs();//储存所有的一级子图(一个）
            ArrayList<Graph> locations = sections.get(0).getSubgraphs();//第一个大图含有所有的地点信息

            setPlayerHome(locations);//创建第一个角色？

            for(Graph currentLocation : locations){
                Node currentLocationDetails = currentLocation.getNodes(false).get(0);
                String currentLocationName = currentLocationDetails.getId().getId();
                String currentLocationDescription = currentLocationDetails.getAttribute("description");
                Location newLocation = new Location(currentLocationName,currentLocationDescription, currentLocationName);
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
            System.out.println("Can not find the file.");
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

    private static boolean loadActionData(File actionsFile){
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            //Document document = builder.parse("config" + File.separator + "extended-actions.xml");
            Document document = builder.parse(actionsFile);
            Element root = document.getDocumentElement();
            NodeList actions = root.getChildNodes();

            for (int i = 1; i < actions.getLength(); i+=2) {
                Element action = (Element) actions.item(i);

                ArrayList<String> consumedEntities = new ArrayList<>();
                ArrayList<String> neededEntities = new ArrayList<>();
                ArrayList<String> producedEntities = new ArrayList<>();

                getActionsFromXML(neededEntities, action, "subjects");

                getActionsFromXML(consumedEntities, action, "consumed");

                getActionsFromXML(producedEntities, action, "produced");

                Element narration = (Element) action.getElementsByTagName("narration").item(0);
                String narrationText = narration.getTextContent();  // 提取文本内容
                //System.out.println(narrationText);

                GameAction newAction = new GameAction(narrationText, consumedEntities, neededEntities, producedEntities);

                getTriggersAndLoadTheHashList(action, newAction);
            }
        } catch(ParserConfigurationException | IOException | SAXException pce) {
            return false;
        }
        return true;
    }

    private static void getActionsFromXML(ArrayList<String> neededEntities, Element action, String keyWord) {
        NodeList subjectsList = action.getElementsByTagName(keyWord);
        for (int t = 0; t < subjectsList.getLength(); t++) { // 遍历所有 <subjects> 元素
            Element subjects = (Element) subjectsList.item(t);
            NodeList subjectEntities = subjects.getElementsByTagName("entity"); // 获取当前 <subjects> 下所有的 <entity>

            for (int j = 0; j < subjectEntities.getLength(); j++) {
                String entity = subjectEntities.item(j).getTextContent(); // 获取 <entity> 文本
                neededEntities.add(entity);
                //System.out.println(" - " + entity);
            }
        }
    }

    private static void getTriggersAndLoadTheHashList(Element action, GameAction newAction){
        NodeList triggersList = action.getElementsByTagName("triggers"); // 获取所有的 <triggers>
        for (int t = 0; t < triggersList.getLength(); t++) { // 遍历所有 <triggers> 元素
            Element triggers = (Element) triggersList.item(t);
            NodeList keyphrases = triggers.getElementsByTagName("keyphrase"); // 获取当前 <triggers> 下所有的 <keyphrase>

            for (int j = 0; j < keyphrases.getLength(); j++) {
                String keyphrase = keyphrases.item(j).getTextContent(); // 获取 <keyphrase> 文本

                HashSet<GameAction> gameActions = new HashSet<>();
                gameActions.add(newAction);
                GameAction.hashActions.put(keyphrase, gameActions);

                //System.out.println(" - " + keyphrase);//获得了两个关键词中的一个
            }
        }
    }

    private static void setPlayerHome(ArrayList<Graph> locations){
        Graph firstLocation = locations.get(0);
        Node locationDetails = firstLocation.getNodes(false).get(0);
        String locationName = locationDetails.getId().getId();
        initialLocation = locationName;
        /*Players newPlayer = new Players("defaultPlayer", locationName);
        Players.playersList.add(newPlayer);*/
    }

    public static void resetTheGameData(){
        GameAction.hashActions.clear();

        Location.locationList.clear();
        Location.theMap.clear();
        Location.artefactsList.clear();
        Location.characterList.clear();
        Location.furnitureList.clear();

        Players.playersList.clear();

        CommandChecker.VALID_ACTIONS.clear();
        CommandChecker.VALID_ENTITIES.clear();
    }

    public static void main(String[] args){
        File entitiesFile = new File("config" + File.separator + "extended-entities.dot");//basic-entities.dot, extended-entities.dot
        File actionsFile = new File("config" + File.separator + "extended-actions.xml");//basic-actions.xml, extended-actions.xml
        loadGameData(entitiesFile, actionsFile);
        System.out.println("1");

        /*CommandParser.handleCommand("Sion: " + "goto forest");
        CommandParser.handleCommand("Sion: " + "get key");
        CommandParser.handleCommand("Sion: " + "goto cabin");
        CommandParser.handleCommand("Sion: " + "open key");
        CommandParser.handleCommand("Li: " + "get key");*/

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.setOut(Output.originalOut);
            System.out.print("Please enter command：");
            String inputString = scanner.nextLine();
            if (inputString.equals("stop")) {
                break;
            }
            //System.out.println("Your command is: " + inputString);
            CommandParser.handleCommand("Li: " + inputString);
            System.out.println(Output.data);
            Output.data.reset();
        }
        scanner.close();
    }
}
