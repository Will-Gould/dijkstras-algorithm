package com.company.pathfinder;

import com.company.Map;
import com.company.Node;
import com.company.Terrain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Dijkstra implements PathFinder{

    private Map map;
    private Node origin;
    private Node checkpoint;
    private Node destination;
    private Node[][] layout;
    private List<Node> visited;

    public Dijkstra(Map map){
        this.map = map;
        this.origin = map.getOrigin();
        this.layout = map.getLayout();
        this.visited = new ArrayList<>();

        findDestination(askStepThrough());
    }

    private boolean askStepThrough() {

        BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
        while(true){
            System.out.print("Would you like to step through the progress? (Y/N): ");
            String selection;
            try{
                selection = reader.readLine();
                boolean valid = selection.equals("Y") || selection.equals("y") || selection.equals("N") || selection.equals("n");
                if(!valid){
                    continue;
                }
                return selection.equals("Y") || selection.equals("y");
            }catch (Exception e){
            }
        }

    }

    @Override
    public List<Node> findPath() {

        List<Node> path = new ArrayList<>();
        Node currentNode = this.destination;
        path.add(currentNode);
        if(this.destination == null){
            return null;
        }
        int x;
        int y;
        do {
            x = currentNode.getX();
            y = currentNode.getY();

            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0 -> {
                        if(x + 1 < this.map.getMaxX()) {
                            if (layout[x + 1][y].getCumCost() + layout[x][y].getCost() == currentNode.getCumCost() && layout[x + 1][y].isClear()) {
                                currentNode = layout[x + 1][y];
                            }
                        }
                    }
                    case 1 -> {
                        if(y - 1 > -1) {
                            if (layout[x][y - 1].getCumCost() + layout[x][y].getCost() == currentNode.getCumCost() && layout[x][y - 1].isClear()) {
                                currentNode = layout[x][y - 1];
                            }
                        }
                    }
                    case 2 -> {
                        if(x - 1 > -1) {
                            if (layout[x - 1][y].getCumCost() + layout[x][y].getCost() == currentNode.getCumCost() && layout[x - 1][y].isClear()) {
                                currentNode = layout[x - 1][y];
                            }
                        }
                    }
                    case 3 -> {
                        if(y + 1 < this.map.getMaxY()) {
                            if (layout[x][y + 1].getCumCost() + layout[x][y].getCost() == currentNode.getCumCost() && layout[x][y + 1].isClear()) {
                                currentNode = layout[x][y + 1];
                            }
                        }
                    }
                }
            }
            path.add(currentNode);
        } while (!currentNode.getTerrain().equals(Terrain.START));

        Collections.reverse(path);

        return path;
    }

    private void findDestination(boolean printProgress) {

        PriorityQueue<Node> toVisit = new PriorityQueue<>();
        Node currentNode = origin;
        Node prevNode = origin;
        this.visited.add(currentNode);
        while(true){
            if(currentNode.getCumCost() > prevNode.getCumCost() && printProgress){
                try{
                    TimeUnit.MILLISECONDS.sleep(500);
                }catch (Exception e){
                    System.out.println("Time error");
                }
                System.out.println("\n");
                map.displayMap();
            }
            int x = currentNode.getX();
            int y = currentNode.getY();
            for(int i = 0; i < 4; i++){
                switch (i){
                    case 0 -> {
                        evaluateAdjacentNode(x + 1, y, currentNode, toVisit);
                    }
                    case 1 ->{
                        evaluateAdjacentNode(x, y - 1, currentNode, toVisit);
                    }
                    case 2 ->{
                        evaluateAdjacentNode(x - 1, y, currentNode, toVisit);
                    }
                    case 3 ->{
                        evaluateAdjacentNode(x, y + 1, currentNode, toVisit);
                    }
                }

            }
            visited.add(currentNode);
            prevNode = currentNode;
            if(toVisit.size() > 0){
                currentNode = toVisit.remove();
            }else{
                System.out.println("Map is impassable");
                break;
            }
            if(currentNode.getTerrain().equals(Terrain.TARGET)){
                this.destination = currentNode;
                System.out.println("Target found!");
                break;
            }
        }
    }

    private void evaluateAdjacentNode(int x, int y, Node currentNode, PriorityQueue<Node> toVisit) {
        if(map.inBounds(x, y) && validNode(layout[x][y])){
            Node node = layout[x][y];
            if(currentNode.getCumCost() + node.getCost() < node.getCumCost() || node.getCumCost() == 0){
                node.setCumCost(currentNode.getCumCost() + node.getCost());
            }
            if(!toVisit.contains(node)){
                toVisit.add(node);
            }
        }
    }

    private boolean validNode(Node n){
        return n.isClear() && !this.visited.contains(n);
    }

    public void displayPath(List<Node> path) {

        if(path == null){
            return;
        }

        Arrays.stream(this.layout).forEach(nodes -> {
            Arrays.stream(nodes)
                    .forEach(node -> {
                        if(path.contains(node)){
                            System.out.print("| P |");
                        }
                        switch(node.getTerrain()){
                            case CLEAR -> {
                                if(!path.contains(node)){
                                    System.out.print("|   |");
                                }
                            }
                            case TARGET -> {
                                if(!path.contains(node)) {
                                    System.out.print("| X |");
                                }
                            }
                            case ROCK -> {
                                System.out.print("| * |");
                            }
                            case WALL -> {
                                System.out.print("| //|");
                            }
                            case START -> {
                                if(!path.contains(node)) {
                                    System.out.print("| O |");
                                }
                            }
                        }
                    });
            System.out.println();
        });

    }
}
