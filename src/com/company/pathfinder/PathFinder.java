package com.company.pathfinder;

import com.company.Node;

import java.util.List;

public interface PathFinder {

    /**
     * Find a shortest path for the map.
     */
    public abstract List<Node> findPath();

}
