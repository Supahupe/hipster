/*
 * Copyright 2013 Centro de Investigación en Tecnoloxías da Información (CITIUS).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.usc.citius.lab.hipster.jung;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import es.usc.citius.lab.hipster.util.maze.Maze2D;
import es.usc.citius.lab.hipster.util.maze.MazeSearch;
import org.apache.commons.collections15.Transformer;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class to obtain an instance of {@link DirectedGraph} (JUNG library) from an
 * instance of {@link Maze2D}.
 *
 * @author Adrián González Sieira <adrian.gonzalez@usc.es>
 * @author Pablo Rodríguez Mier <pablo.rodriguez.mier@usc.es>
 * @version 1.0
 * @since 26-03-2013
 */
public class JungUtils {

    /**
     * Conversion process.
     *
     * @param maze instance of {@link Maze2D}
     * @return instance of {@link DirectedGraph}
     */
    public static DirectedGraph<Point, JungEdge<Point>> createGraphFromMaze(Maze2D maze) {
        // Create a graph from maze
        DirectedGraph<Point, JungEdge<Point>> graph = new DirectedSparseGraph<Point, JungEdge<Point>>();
        // Convert maze to graph. For each cell, add all valid neighbors with
        // their costs
        for (Point source : maze.getMazePoints()) {
            if (!graph.containsVertex(source)) {
                graph.addVertex(source);
            }
            for (Point dest : maze.validLocationsFrom(source)) {
                if (!graph.containsVertex(dest)) {
                    graph.addVertex(dest);
                }
                double edgeCost = Math.sqrt((source.x - dest.x)
                        * (source.x - dest.x) + (source.y - dest.y)
                        * (source.y - dest.y));
                JungEdge<Point> e = new JungEdge<Point>(source, dest, edgeCost);
                if (!graph.containsEdge(e)) {
                    graph.addEdge(e, source, dest);
                }
            }
        }
        return graph;
    }

    public static <V,E> DijkstraShortestPath<V,E> createUnweightedDijkstraAlgorithm(Graph<V,E> graph, boolean cache){
        return new DijkstraShortestPath<V,E>(graph, new Transformer<E, Double>() {
            @Override
            public Double transform(E e) {
                return 1.0d;
            }
        }, cache);

    }

    public static <V,E> List<E> runUnweightedDijkstra(Graph<V, E> jungGraph, V initial, V goal, boolean cache) {
        DijkstraShortestPath<V,E> dijkstra = createUnweightedDijkstraAlgorithm(jungGraph, cache);

        return dijkstra.getPath(initial, goal);
    }


}