package com.company;

import com.company.pathfinder.Dijkstra;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Map map = null;
        BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
        int choice = 0;
        while(choice != 4){
            displayMenu();
            System.out.print("input: ");
            try{
                choice = Integer.parseInt(reader.readLine());
                if(choice < 1 || choice > 4){
                    System.out.println("Invalid input");
                    choice = 0;
                }
            }catch (Exception e){
                choice = 0;
                System.out.println("Invalid input");
                continue;
            }

            switch (choice) {
                case 1 -> printGuide();
                case 2 -> map = chooseMap();
                case 3 -> runAlgorithm(map);
                case 4 -> System.out.println("Quitting...");
            }

        }


    }

    private static void runAlgorithm(Map map) {

        if(map == null){
            System.out.println("Error getting map, please choose a new map.");
            return;
        }

        BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
        int input = 0;
        while(input != 2){
            System.out.println("Dijkstra's Algorithm---(1):");
            System.out.println("Cancel-----------------(2):");
            System.out.print("input: ");

            try{
                input = Integer.parseInt(reader.readLine());
                if(input < 1 || input > 2){
                    continue;
                }
            }catch(Exception e){
            }

            switch (input){
                case 1 -> {
                    dijkstra(map);
                    map.resetMap();
                }
                case 2 -> System.out.println("...");
            }

        }

    }

    private static void dijkstra(Map map) {

        Dijkstra dijkstra = new Dijkstra(map);
        dijkstra.displayPath(dijkstra.findPath());

    }

    private static void displayMenu(){
        System.out.println("Show guide------(1):");
        System.out.println("Load map--------(2):");
        System.out.println("Run algorithm---(3):");
        System.out.println("Exit------------(4):");
    }

    private static void printGuide(){
        System.out.println("==========Map Guide==========");
        System.out.println("Cells and Terrain:");
        System.out.println("  |   | - Empty Cell");
        System.out.println("  | X | - Target Cell");
        System.out.println("  | O | - Starting node");
        System.out.println("  | P | - Path from start to target");
        System.out.println("Impassible terrain:");
        System.out.println("  | //| - Wall");
        System.out.println("  |  *| - Rock");
        System.out.println();
    }

    private static Map chooseMap(){
        boolean mapConfirm = false;
        List<Map> maps = loadMaps();
        Map map = null;
        BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
        while(!mapConfirm){

            int userSelection = 0;

            System.out.print("Select map from: [ ");
            for(int i = 0; i < maps.size(); i++){
                if(i == maps.size() - 1){
                    System.out.printf("%d ]", i + 1);
                    continue;
                }
                System.out.printf("%d, ", i + 1);
            }
            System.out.println();

            try{
                userSelection = Integer.parseInt(reader.readLine());
            }catch (Exception e){
                System.out.println("Please enter a valid integer!");
                continue;
            }
            if(userSelection < 1 || userSelection > maps.size()){
                System.out.println("Please enter within the valid range!");
                continue;
            }

            //User has a valid choice
            //Load and display the map and ask for confirmation
            map = maps.get(userSelection - 1);
            map.displayMap();

            boolean hasStart = map.getHasStart();
            boolean hasFinish = map.getHasFinish();
            if(!hasStart && !hasFinish){
                System.out.println("This map is missing a start and a finish");
            }else{
                if(!hasStart){
                    System.out.println("This map is missing a start");
                }else{
                    if(!hasFinish){
                        System.out.println("This map is missing a finish");
                    }
                }
            }

            while(true){
                System.out.print("Confirm map (Y/N): ");
                String selection;
                try{
                    selection = reader.readLine();
                    boolean valid = selection.equals("Y") || selection.equals("y") || selection.equals("N") || selection.equals("n");
                    if(!valid){
                        continue;
                    }
                    if(selection.equals("Y") || selection.equals("y")){
                        mapConfirm = true;
                    }
                    break;
                }catch (Exception e){
                }
            }

        }

        if(!map.getHasStart()){
            while(true){
                System.out.print("Choose a start position (x,y): ");
                String selection;
                try{
                    selection = reader.readLine();
                    String[] input = selection.split(",");
                    if(!(input.length == 2)){
                        continue;
                    }

                    int y = Integer.parseInt(input[0]) - 1;
                    int x = map.getMaxX() - Integer.parseInt(input[1]);

                    if(!map.inBounds(x, y) || !map.getLayout()[x][y].getTerrain().equals(Terrain.CLEAR)){
                        System.out.println("Invalid coordinates");
                        continue;
                    }

                    map.getLayout()[x][y].setTerrain(Terrain.START);
                    map.setOrigin(map.getLayout()[x][y]);
                    break;

                }catch (Exception e){
                    System.out.println("Invalid input");
                }
            }
            map.displayMap();
            System.out.println("Start placed");
        }

        if(!map.getHasFinish()){
            while(true){
                System.out.print("Choose a finish position (x,y): ");
                String selection;
                try{
                    selection = reader.readLine();
                    String[] input = selection.split(",");
                    if(!(input.length == 2)){
                        continue;
                    }

                    int y = Integer.parseInt(input[0]) - 1;
                    int x = map.getMaxX() - Integer.parseInt(input[1]);

                    if(!map.inBounds(x, y) || !map.getLayout()[x][y].getTerrain().equals(Terrain.CLEAR)){
                        System.out.println("Invalid coordinates");
                        continue;
                    }

                    map.getLayout()[x][y].setTerrain(Terrain.TARGET);
                    map.setDestination(map.getLayout()[x][y]);
                    break;

                }catch (Exception e){
                    System.out.println("Invalid input");
                }
            }
            map.displayMap();
            System.out.println("Finish placed");
        }

        return map;
    }

    private static List<Map> loadMaps() {

        ArrayList<Map> maps = new ArrayList<>();
        maps.add(new Map(
                "one", 1
        ));
        maps.add(new Map(
                "two", 2
        ));
        maps.add(new Map(
           "three", 3
        ));
        maps.add(new Map(
                "four", 4
        ));
        maps.add(new Map(
                "five", 5
        ));
        maps.add(new Map(
                "six", 6
        ));
        return maps;
    }
}
