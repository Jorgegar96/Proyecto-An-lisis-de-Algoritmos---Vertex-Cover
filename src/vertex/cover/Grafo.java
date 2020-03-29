/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vertex.cover;

import java.util.LinkedList;

/**
 *
 * @author jorge
 */
public class Grafo {
    
    //Número de vértices
    private int vertexCount;
    
    //Arreglo que contendrá las listas de adyacencia
    private LinkedList<Integer> adjacentVertices[];
    
    //Constructor que recibe el número total de vértices
    Grafo(int vertexCount){
        this.vertexCount = vertexCount;
        adjacentVertices = new LinkedList[vertexCount];
        for(int i=0; i<vertexCount; i++){
            adjacentVertices[i] = new LinkedList();
        }
    }
    
    //Metodo para agregar una arista al grafo no-dirigido
    public void agregarArista(int v, int w){
        adjacentVertices[v].add(w);
        adjacentVertices[w].add(v);
    }
    
    //Ver número de vértices
    public int getVertexCount(){
        return this.vertexCount;
    }
    
    public LinkedList<Integer>[] getAdjacentVertices(){
        return this.adjacentVertices;
    }
    
}
