package com.company;

import java.util.Arrays;

public class Map {

    private Node[][] layout;
    private String name;
    private boolean hasStart = false;
    private boolean hasFinish = false;
    private Node origin;
    private Node destination;
    private int maxX;
    private int maxY;

    public Map(String name, int x){
        this.name = name;
        this.layout = loadMap(x);
        this.hasStart = hasStart();
        this.hasFinish = hasFinish();
    }

    public int getMaxX(){
        return this.maxX;
    }

    public int getMaxY(){
        return this.maxY;
    }

    public Node getOrigin(){
        return this.origin;
    }

    public void setOrigin(Node origin) {
        this.origin = origin;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public Node getDestination(){
        return this.destination;
    }

    public boolean getHasStart(){
        return this.hasStart;
    }

    public boolean getHasFinish(){
        return this.hasFinish;
    }

    public boolean inBounds(int x, int y){
        if(x > this.maxX - 1 || x < 0){
            return false;
        }
        if(y > this.maxY - 1 || y < 0){
            return false;
        }
        return true;
    }

    public boolean inXBounds(int x){
        return x <= maxX - 1 && x >= 0;
    }

    public boolean inYBounds(int y){
        return y <= maxY - 1 && y >= 0;
    }

    private boolean hasStart(){
        for(int i = 0; i < maxX; i++){
            for(int j = 0; j < maxY; j++){
                if(layout[i][j].getTerrain().equals(Terrain.START)){
                    this.origin = layout[i][j];
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasFinish(){
        for(int i = 0; i < maxX; i++){
            for(int j = 0; j < maxY; j++){
                if(layout[i][j].getTerrain().equals(Terrain.TARGET)){
                    this.destination = layout[i][j];
                    return true;
                }
            }
        }
        return false;
    }

    public void displayMap(){

        for(int i = 0; i < this.maxX + 1; i++){
            for(int j = 0; j < this.maxY + 1; j++){
                if(i == this.maxX){
                    if(j == 0){
                        System.out.print(cellFormat("y/x"));
                    }else{
                        System.out.print(cellFormat(Integer.toString(j)));
                    }
                    continue;
                }
                if(j == 0){
                    System.out.print(cellFormat(Integer.toString(this.maxX - i)));
                    continue;
                }
                switch(layout[i][j - 1].getTerrain()){
                    case CLEAR -> {
                        if(layout[i][j - 1].getCumCost() == 0){
                            System.out.print(cellFormat(" "));
                        }else{
                            System.out.print(cellFormat(Integer.toString(layout[i][j - 1].getCumCost())));
                        }
                    }
                    case TARGET -> {
                        System.out.print(cellFormat("X"));
                    }
                    case ROCK -> {
                        System.out.print(cellFormat("*"));
                    }
                    case WALL -> {
                        System.out.print(cellFormat("//"));
                    }
                    case START -> {
                        System.out.print(cellFormat("O"));
                    }
                }
            }
            System.out.println();
        }
    }

    private String cellFormat(String x){

        int maxLength = Integer.toString(maxX * maxY).length() + 2;
        StringBuilder cell = new StringBuilder("|");
        int valueLength = x.length();

        int quotient = (maxLength - valueLength) / 2;
        int remainder = (maxLength - valueLength) % 2;
        //Add left side spaces
        cell.append(" ".repeat(Math.max(0, quotient)));

        //Add value
        cell.append(x);

        //Add right side spaces
        cell.append(" ".repeat(Math.max(0, quotient + remainder)));

        cell.append("|");
        return cell.toString();

    }

    private Node[][] loadMap(int x){
        Node[][] map;
        int mapX = 0;
        int mapY = 0;
        if(x == 1){
            mapX = 16;
            mapY = 16;
            this.maxX = mapX;
            this.maxY = mapY;
            map = new Node[mapX][mapY];
            for(int i = 0; i < mapX; i++){
                for(int j = 0; j < mapY; j++){
                    //Rocks
                    if((i < 6 && i > 0) && j == 2){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(i == 5 && (j < 9 && j > 4)){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(i == 6 && j == 3){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(i == 7 && j == 4){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }

                    //Walls
                    if(i > 7 && j == 12){
                        map[i][j] = new Node(Terrain.WALL, i, j);
                        continue;
                    }

                    //Start and Target
                    if(i == 7 && j == 1){
                        map[i][j] = new Node(Terrain.START, i, j);
                        continue;
                    }
                    if(i == 8 && j == 15){
                        map[i][j] = new Node(Terrain.TARGET, i, j);
                        continue;
                    }

                    //Clear Terrain
                    map[i][j] = new Node(Terrain.CLEAR, i, j);

                }
            }
            return map;
        }
        if(x == 2){
            mapX = 32;
            mapY = 32;
            this.maxX = mapX;
            this.maxY = mapY;
            map = new Node[mapX][mapY];
            for(int i = 0; i < mapX; i++){
                for(int j = 0; j < mapY; j++){
                    //Rocks
                    if((i < 6 && i > 0) && j == 2){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(i == 5 && (j < 9 && j > 4)){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(i == 6 && j == 3){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(i == 7 && j == 4){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }

                    if((i > 20 && i < 24) && j == 1){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(i == 23 &&(j > 1 && j < 4)){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }

                    //Walls
                    if((j > 1 && j < 13) && (i > 19 && i < 23)){
                        map[i][j] = new Node(Terrain.WALL, i, j);
                        continue;
                    }
                    if((i > 7 && i < 16) && j == 12){
                        map[i][j] = new Node(Terrain.WALL, i, j);
                        continue;
                    }

                    //Clear Terrain
                    map[i][j] = new Node(Terrain.CLEAR, i, j);

                }
            }
            return map;
        }
        if(x == 3){
            mapX = 16;
            mapY = 16;
            this.maxX = mapX;
            this.maxY = mapY;
            map = new Node[mapX][mapY];
            for(int i = 0; i < mapX; i++){
                for(int j = 0; j < mapY; j++){
                    //Walls
                    if((i > 0 && i < 15) && j == 8){
                        map[i][j] = new Node(Terrain.WALL, i, j);
                        continue;
                    }

                    //Start and Target
                    if(i == 8 && j == 0){
                        map[i][j] = new Node(Terrain.START, i, j);
                        continue;
                    }
                    if(i == 8 && j == 15){
                        map[i][j] = new Node(Terrain.TARGET, i, j);
                        continue;
                    }

                    //Clear Terrain
                    map[i][j] = new Node(Terrain.CLEAR, i, j);

                }
            }
            return map;
        }
        if(x == 4){
            mapX = 32;
            mapY = 32;
            this.maxX = mapX;
            this.maxY = mapY;
            map = new Node[mapX][mapY];
            for(int i = 0; i < mapX; i++){
                for(int j = 0; j < mapY; j++){
                    //Rocks
                    if(!(i == 0) || !(j == 0)){
                        if(j + i == mapY - 1){
                            map[i][j] = new Node(Terrain.ROCK, i, j);
                            continue;
                        }
                    }

                    //Start and Target
                    if(i == 0 && j == 0){
                        map[i][j] = new Node(Terrain.START, i, j);
                        continue;
                    }
                    if(i == 31 && j == 31){
                        map[i][j] = new Node(Terrain.TARGET, i, j);
                        continue;
                    }

                    //Clear Terrain
                    map[i][j] = new Node(Terrain.CLEAR, i, j);

                }
            }
            return map;
        }
        if(x == 5){
            mapX = 32;
            mapY = 32;
            this.maxX = mapX;
            this.maxY = mapY;
            map = new Node[mapX][mapY];
            for(int i = 0; i < mapX; i++){
                for(int j = 0; j < mapY; j++){
                    //Rocks
                    if(!(i == 0) && !(j == 0)){
                        if(j + i == mapY - 1){
                            map[i][j] = new Node(Terrain.ROCK, i, j);
                            continue;
                        }
                    }

                    //Start and Target
                    if(i == 0 && j == 0){
                        map[i][j] = new Node(Terrain.START, i, j);
                        continue;
                    }
                    if(i == 31 && j == 31){
                        map[i][j] = new Node(Terrain.TARGET, i, j);
                        continue;
                    }

                    //Clear Terrain
                    map[i][j] = new Node(Terrain.CLEAR, i, j);

                }
            }
            return map;
        }
        if(x == 6){
            mapX = 32;
            mapY = 32;
            this.maxX = mapX;
            this.maxY = mapY;
            map = new Node[mapX][mapY];
            for(int i = 0; i < mapX; i++){
                for(int j = 0; j < mapY; j++){
                    //Rocks
                    if(!(i == 0) && !(j == 0)){
                        if(j + i == mapY - 1){
                            map[i][j] = new Node(Terrain.ROCK, i, j);
                            continue;
                        }
                    }
                    if(i == 23 && j == 9){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(i == 24 && j == 10){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(i == 25 && j == 11){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(i == 26 && j == 12){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(i == 27 && j == 13){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(i == 28 && j == 14){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(i == 29 && j == 15){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(i == 30 && j == 16){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(i == 31 && j == 17){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }

                    if(j == 23 && i == 9){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(j == 24 && i == 10){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(j == 25 && i == 11){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(j == 26 && i == 12){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(j == 27 && i == 13){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(j == 28 && i == 14){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(j == 29 && i == 15){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(j == 30 && i == 16){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }
                    if(j == 31 && i == 17){
                        map[i][j] = new Node(Terrain.ROCK, i, j);
                        continue;
                    }

                    //Start and Target
                    if(i == 0 && j == 0){
                        map[i][j] = new Node(Terrain.START, i, j);
                        continue;
                    }
                    if(i == 31 && j == 31){
                        map[i][j] = new Node(Terrain.TARGET, i, j);
                        continue;
                    }

                    //Clear Terrain
                    map[i][j] = new Node(Terrain.CLEAR, i, j);

                }
            }
            return map;
        }
        return null;
    }

    public Node[][] getLayout() {
        return this.layout;
    }

    public void resetMap(){
        for(int i = 0; i < this.maxX; i++){
            for(int j = 0; j < this.getMaxY(); j++){
                this.layout[i][j].setCumCost(0);
            }
        }
    }

}
