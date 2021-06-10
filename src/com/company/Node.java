package com.company;

public class Node implements Comparable<Node>{

    private Terrain terrain;
    private final int x;
    private final int y;
    private final int cost;
    private int cumCost;

    public Node(Terrain terrain, int x, int y){
        this.terrain = terrain;
        this.x = x;
        this.y = y;
        this.cost = 1;
        this.cumCost = 0;
    }

    public int getCumCost() {
        return cumCost;
    }

    public void setCumCost(int cumCost) {
        this.cumCost = cumCost;
    }

    public int getCost(){
        return this.cost;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Terrain getTerrain(){
        return this.terrain;
    }

    public void setTerrain(Terrain terrain){
        this.terrain = terrain;
    }

    public boolean isClear(){
        return !this.terrain.equals(Terrain.WALL) && !this.terrain.equals(Terrain.ROCK);
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.getCumCost(), o.getCumCost());
    }
}
