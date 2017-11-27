/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.visual.graph.layout.hierarchicalsupport;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.graph.layout.UniversalGraph;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author ptliu
 */
public class DirectedGraph<N, E> {

    private Collection<N> nodes;
    private Collection<Vertex> rootVertices;
    private Collection<Vertex> vertices;
    private Collection<E> edges;
    private Map<N, Vertex> vertexMap;
    private Map<E, Edge> edgeMap;
    private GraphScene scene;
    private UniversalGraph<N, E> uGraph;

    /**
     * Creates a new instance of DirectedGraph
     */
    protected DirectedGraph(UniversalGraph<N, E> uGraph, GraphScene scene) {
        this.uGraph = uGraph;
        this.scene = scene;
        this.nodes = uGraph.getNodes();
        this.edges = uGraph.getEdges();

        vertexMap = new HashMap<N, Vertex>();
        edgeMap = new LinkedHashMap<E, Edge>();
        rootVertices = new ArrayList<Vertex>();
        vertices = new ArrayList<Vertex>();
    }

    public static <N, E> DirectedGraph createGraph(UniversalGraph<N, E> uGraph, GraphScene scene) {
        DirectedGraph<N, E> graph = new DirectedGraph<N, E>(uGraph, scene);
        graph.createGraph();
        //graph.printGraph();
        return graph;
    }

    protected void createGraph() {
        for (E e : edges) {

            N source = uGraph.getEdgeSource(e);
            N target = uGraph.getEdgeTarget(e);

            Vertex sourceVertex = getVertex(source);
            Vertex targetVertex = getVertex(target);

            Edge edge = createEdge(sourceVertex, targetVertex, e);

            sourceVertex.addOutgoingEdge(edge);
            targetVertex.addIncomingEdge(edge);

            sourceVertex.addUpperNeighbor(targetVertex);
            targetVertex.addLowerNeighbor(sourceVertex);

        }

        for (N node : nodes) {
            Vertex vertex = getVertex(node);

            Widget widget = scene.findWidget(node);
            if (widget == null) continue ;  //why is it null
            Rectangle bounds = widget.getBounds();

            Dimension size = new Dimension(bounds.width, bounds.height);

            vertex.setSize(size);
        }


        findRootVertices();
    //printGraph();
    }

    private Vertex getVertex(N node) {
        Vertex vertex = vertexMap.get(node);

        if (vertex == null) {
            vertex = createVertex(node);
            //vertices.add(vertex);
            vertexMap.put(node, vertex);
        }

        return vertex;
    }

    protected Vertex createVertex(N node) {
        return new Vertex(node);
    }

    protected Edge createEdge(Vertex source, Vertex target, E edgeDE) {
        return new Edge(source, target, edgeDE);
    }

    private Edge getEdge(Vertex source, Vertex target, E edgeDE) {
        Edge edge = edgeMap.get(edgeDE);

        if (edge == null) {
            edge = createEdge(source, target, edgeDE);
            edgeMap.put(edgeDE, edge);
        }

        return edge;
    }

    public Collection<Vertex> getVertices() {
        return vertexMap.values();
    }

    public Collection<Vertex> getRootVertices() {
        return rootVertices;
    }

    private void findRootVertices() {
        for (Vertex vertex : vertexMap.values()) {
            Collection<Vertex> uppers = vertex.getUpperNeighbors();
            if (uppers.size() == 0) {
                rootVertices.add(vertex);
            }
        }
    }

    public DummyVertex insertDummyVertex(Edge edge, DummyVertex.Type type) {
        Edge originalEdge = edge;

        if (edge instanceof DummyEdge) {
            originalEdge = ((DummyEdge) edge).getOriginalEdge();
        }

        DummyVertex dv = createDummyVertex(originalEdge, type);
        vertices.add(dv);

        Vertex source = edge.getSource();
        Vertex target = edge.getTarget();

        source.removeOutgoingEdge(edge);
        source.removeLowerNeighbor(target);
        source.addLowerNeighbor(dv);
        dv.addUpperNeighbor(target);
        DummyEdge de = createDummyEdge(source, dv, originalEdge);
        source.addOutgoingEdge(de);
        dv.addIncomingEdge(de);

        target.removeIncomingEdge(edge);
        target.removeUpperNeighbor(source);
        target.addUpperNeighbor(dv);
        dv.addLowerNeighbor(target);
        de = createDummyEdge(dv, target, originalEdge);
        target.addIncomingEdge(de);
        dv.addOutgoingEdge(de);

        return dv;
    }

    protected DummyVertex createDummyVertex(Edge originalEdge, DummyVertex.Type type) {
        return new DummyVertex(originalEdge, type);
    }

    public DummyEdge addDummyEdge(Vertex source, Vertex target) {
        DummyEdge de = createDummyEdge(source, target, null);
        source.addOutgoingEdge(de);
        target.addIncomingEdge(de);
        source.addLowerNeighbor(target);
        target.addUpperNeighbor(source);

        return de;
    }

    protected DummyEdge createDummyEdge(Vertex source, Vertex target,
            Edge originalEdge) {
        return new DummyEdge(source, target, originalEdge);
    }

    private void printGraph() {
        for (Vertex rootVertex : getRootVertices()) {
            System.out.println("root vertex = " + rootVertex);
        }

        for (Vertex v : getVertices()) {
            System.out.println("vertex = " + v);

            Collection<Vertex> neighbors = v.getUpperNeighbors();
            for (Vertex nv : neighbors) {
                System.out.println("\tupper neighbor = " + nv);
            }

            neighbors = v.getLowerNeighbors();
            for (Vertex nv : neighbors) {
                System.out.println("\tlower neighbor = " + nv);
            }
        }

//    protected void createGraph(NodeDesignElement rootNode) {
//        Vertex rootVertex = getVertex(rootNode);
//        
//        for (EdgeDesignElement edge : rootNode.getOutputEdges()) {
//            NodeDesignElement destNode = edge.getTargetNode();
//            
//            if (vertexMap.get(destNode) != null)
//                continue;
//            
//            //System.out.println("edge = " + edge);
//            Vertex lowerVertex = getVertex(destNode);
//            rootVertex.addLowerNeighbor(lowerVertex);
//            lowerVertex.addUpperNeighbor(rootVertex);
//            
//            createGraph(destNode);
//        }
//        
//        for (EdgeDesignElement edge : rootNode.getInputEdges()) {
//            NodeDesignElement srcNode = edge.getSourceNode();
//            
//            if (vertexMap.get(srcNode) != null)
//                continue;
//            
//            //System.out.println("edge = " + edge);
//            Vertex lowerVertex = getVertex(srcNode);
//            rootVertex.addLowerNeighbor(lowerVertex);
//            lowerVertex.addUpperNeighbor(rootVertex);
//            
//            createGraph(srcNode);
//        }
//    }       
//    protected DirectedGraph(Collection<NodeDesignElement> nodes) {
//        this.nodes = nodes;
//        vertexMap = new HashMap<NodeDesignElement, Vertex>();
//        edgeMap = new HashMap<EdgeDesignElement, Edge>();
//        rootVertices = new ArrayList<Vertex>();
//        vertices = new ArrayList<Vertex>();
//    }
    /**
     *
     *
     */
//    public static DirectedGraph createGraph(Collection<NodeDesignElement> nodes) {
//        DirectedGraph graph = new DirectedGraph(nodes);
//        graph.createGraph();
//        return graph;
//    }
//    
//    /**
//     *
//     *
//     */
//    public static DirectedGraph createGraph(Collection<NodeDesignElement> nodes,
//            NodeDesignElement rootNode) {
//        DirectedGraph graph = new DirectedGraph(nodes);
//        graph.createGraph(rootNode);
//        graph.findRootVertices();
//        return graph;
//    }
    }

    /**
     *
     *
     */
    public static class Vertex<N> {

        private N nodeDE;
        private ArrayList<Vertex> upperNeighbors;
        private ArrayList<Vertex> lowerNeighbors;
        private Collection<Edge> incomingEdges;
        private Collection<Edge> outgoingEdges;
        private int number = -1;
        private int x;
        private int y;
        private Object vertexData;
        private Dimension size = new Dimension(0, 0);

        /** Creates a new instance of Vertex */
        public Vertex(N nodeDE) {
            this.nodeDE = nodeDE;
            upperNeighbors = new ArrayList<Vertex>();
            lowerNeighbors = new ArrayList<Vertex>();
            incomingEdges = new ArrayList<Edge>();
            outgoingEdges = new ArrayList<Edge>();
        }

        public Dimension getSize() {
            return size;
        }

        public void setSize(Dimension dim) {
            this.size = dim;
        }

        /**
         *
         *
         */
        public N getNodeDesignElement() {
            return nodeDE;
        }

        /**
         *
         *
         */
        public int getNumber() {
            return number;
        }

        /**
         *
         *
         */
        public void setNumber(int number) {
            this.number = number;
        }

        /**
         *
         *
         */
        public int getX() {
            return x;
        }

        /**
         *
         *
         */
        public void setX(int x) {
            this.x = x;
        }

        /**
         *
         *
         */
        public int getY() {
            return y;
        }

        /**
         *
         *
         */
        public void setY(int y) {
            this.y = y;
        }

        /**
         *
         *
         */
        public void addLowerNeighbor(Vertex vertex) {
            if (!lowerNeighbors.contains(vertex)) {
                lowerNeighbors.add(vertex);
            }
        }

        /**
         *
         *
         */
        public void removeLowerNeighbor(Vertex vertex) {
            lowerNeighbors.remove(vertex);
        }

        /**
         *
         *
         */
        public void replaceLowerNeighbor(Vertex oldVertex, Vertex newVertex) {
            lowerNeighbors.set(lowerNeighbors.indexOf(oldVertex), newVertex);
        }

        /**
         *
         *
         */
        public Collection<Vertex> getLowerNeighbors() {
            return Collections.unmodifiableCollection(lowerNeighbors);
        }

        /**
         *
         *
         */
        public void addUpperNeighbor(Vertex vertex) {
            if (!upperNeighbors.contains(vertex)) {
                upperNeighbors.add(vertex);
            }
        }

        /**
         *
         *
         */
        public void removeUpperNeighbor(Vertex vertex) {
            upperNeighbors.remove(vertex);
        }

        /**
         *
         *
         */
        public void replaceUpperNeighbor(Vertex oldVertex, Vertex newVertex) {
            upperNeighbors.set(upperNeighbors.indexOf(oldVertex), newVertex);
        }

        /**
         *
         *
         */
        public Collection<Vertex> getUpperNeighbors() {
            return Collections.unmodifiableCollection(upperNeighbors);
        }

        /**
         *
         *
         */
        public Collection<Edge> getOutgoingEdges() {
            return outgoingEdges;
        }

        /**
         *
         *
         */
        public void addOutgoingEdge(Edge edge) {
            if (!outgoingEdges.contains(edge)) {
                outgoingEdges.add(edge);
            }
        }

        /**
         *
         *
         */
        public Collection<Edge> getIncomingEdges() {
            return incomingEdges;
        }

        /**
         *
         *
         */
        public void removeOutgoingEdge(Edge edge) {
            outgoingEdges.remove(edge);
        }

        /**
         *
         *
         */
        public void addIncomingEdge(Edge edge) {
            if (!incomingEdges.contains(edge)) {
                incomingEdges.add(edge);
            }
        }

        /**
         *
         *
         */
        public void removeIncomingEdge(Edge edge) {
            incomingEdges.remove(edge);
        }

        /**
         *
         *
         */
        public Edge getEdgeToLowerNeighbor(Vertex nv) {
            Collection<Edge> edges = nv.getOutgoingEdges();

            for (Edge edge : edges) {
                if (edge.getTarget() == nv) {
                    return edge;
                }
            }

            return getEdgeToUpperNeighbor(nv);
        }

        /**
         *
         *
         */
        public Edge getEdgeToUpperNeighbor(Vertex nv) {
            Collection<Edge> edges = nv.getIncomingEdges();

            for (Edge edge : edges) {
                if (edge.getSource() == nv) {
                    return edge;
                }
            }

            return getEdgeToLowerNeighbor(nv);
        }

        /**
         *
         *
         */
        public void setVertexData(Object data) {
            this.vertexData = data;
        }

        /**
         *
         *
         */
        public Object getVertexData() {
            return vertexData;
        }

        /**
         *
         *
         */
        public String toString() {
            return super.toString() + " : " + nodeDE;
        }
    }

    /**
     *
     *
     */
    public static class Edge<E> {

        private Vertex source;
        private Vertex target;
        private E edgeDE;

        /**
         *
         *
         */
        public Edge(Vertex source, Vertex target, E edgeDE) {
            this.source = source;
            this.target = target;
            this.edgeDE = edgeDE;
        }

        /**
         *
         *
         */
        public Vertex getSource() {
            return source;
        }

        /**
         *
         *
         */
        public Vertex getTarget() {
            return target;
        }

        /**
         *
         *
         */
        public E getEdgeDesignElement() {
            return edgeDE;
        }
    }

    /**
     *
     *
     */
    public static class DummyVertex extends Vertex {

        private static int counter = 0;

        public enum Type {

            CROSSING, HYPEREDGE, BEND, TEMPORARY
        }

        
        
          ;
        private  Edge originalEdge;
        private   Type 

         

         type ;   
            private int index;
        
        public DummyVertex(Edge originalEdge, Type type) {
            super(null);
            this.originalEdge = originalEdge;
            this.type = type;
            index = --counter;
        }

        public DummyVertex(Type type) {
            this(null, type);
        }

        public void setOriginalEdge(Edge originalEdge) {
            this.originalEdge = originalEdge;
        }

        public Edge getOriginalEdge() {
            return originalEdge;
        }

        public Type getType() {
            return type;
        }

        public String toString() {
            return "dummy vertex " + index;
        }
    }

    /**
     *
     *
     */
    public static class DummyEdge extends Edge {

        private Edge originalEdge;

        public DummyEdge(Vertex source, Vertex target, Edge originalEdge) {
            super(source, target, null);
            this.originalEdge = originalEdge;
        }

        public DummyEdge(Vertex source, Vertex target) {
            this(source, target, null);
        }

        public void setOriginalEdge(Edge originalEdge) {
            this.originalEdge = originalEdge;
        }

        public Edge getOriginalEdge() {
            return originalEdge;
        }

        public String toString() {
            return "dummy " + super.toString();
        }
    }
}
