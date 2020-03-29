/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vertex.cover;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author jorge
 */
public class VertexCover {
    
    Scanner sc = new Scanner(System.in);

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
       
        for (int indice = 1; indice <= 5; indice++) {
            
            System.out.println(
                "**************Probando con el grafo "+indice+"**************"
            );
            
            File file = new File("grafo"+indice+".txt");
            FileReader fr = new FileReader(file); 

            BufferedReader br = new BufferedReader(fr);

            int numeroDeVertices = Integer.parseInt(br.readLine());

            Grafo g = new Grafo(numeroDeVertices); 
            String linea = null;

            while((linea = br.readLine()) != null){
                String[] arista = linea.trim().split(",");
                g.agregarArista(
                    Integer.parseInt(arista[0]), 
                    Integer.parseInt(arista[1])
                ); 
            }

            //Toma de datos para el tiempo de inicio
            long startTime1 = System.nanoTime();

            //Ejecución del Algoritmo 
            LinkedList<Integer> solucionAproximada = vertexCoverAproximado(g);

            //Toma de datos para el tiempo final
             long endTime1 = System.nanoTime();

            //Tiempo de duración
            long durationInNano1 = (endTime1 - startTime1);

            System.out.println("-------------------------------------------------");
            System.out.println("Vértices del Vertex Cover Mínimo Aproximado:");
            for (int i=0; i < solucionAproximada.size(); i++) {
                System.out.print(solucionAproximada.get(i));
                if(i<solucionAproximada.size()-1)
                    System.out.print(", ");
            }
            System.out.println("\n");
            System.out.println("Tiempo de Duración en nanosegundos:\n" + durationInNano1+"ns");
            System.out.println("-------------------------------------------------");

            //Toma de datos para el tiempo de inicio
            long startTime2 = System.nanoTime();

            //Ejecución del algoritmo
            int[] solucionNoOptima = vertexCoverNoOptimo(g);

            //Toma de datos para el tiempo final
             long endTime2 = System.nanoTime();

            //Tiempo de duración
            long durationInNano2 = (endTime2 - startTime2);

            //System.out.println("-------------------------------------------------");
            System.out.println("Vértices del Vertex Cover Mínimo No Óptimo:");
            for (int i = 0; i < solucionNoOptima.length; i++) {
                System.out.print(solucionNoOptima[i]);
                if(i<solucionNoOptima.length-1)
                    System.out.print(", ");
            }
            System.out.println("\n");
            System.out.println("Tiempo de Duración en nanosegundos:\n" + durationInNano2+"ns");
            System.out.println("-------------------------------------------------");

            /*
            List<int[]> l = generate(5,3);
            for (int i = 0; i < l.size(); i++) {
                for (int j = 0; j < l.get(i).length; j++) {
                    System.out.print(l.get(i)[j]);
                }
                System.out.println("");
            }
            */
            System.out.println(
                "**************Fin de prueba del grafo "+indice+"**************\n\n"
            );
        }
    }
    
    /**
     * Método aproximado para resolver el problema de vertex cover
     * @param g
     * @return el vertex cover mínimo aproximado
     */
    public static LinkedList<Integer> vertexCoverAproximado(Grafo g){
        
        //Se inicializa un arreglo de booleanos correspondiente a los vértices
        //del grafo g y se setean todos como no-visitados
        int vertexCount = g.getVertexCount();
        boolean visited[] = new boolean[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            visited[i] = false;
        }
        
        //Se trae la lista de adyacencia del grafo g
        LinkedList<Integer>[] adyacentVertices = g.getAdjacentVertices();
        
        //Aquí se guardarán las aristas pertenecientes al conjunto solución
        LinkedList<Integer> vertexCoverList = new LinkedList();
        
        //Se elige el vértice u de forma aleatoria utilzando una instancia
        //de la clase Random y un límite del total de vértices en el grafo
        int initialVertex = (new Random()).nextInt(vertexCount);
        
        //Se recorren todos los vértices uno por uno
        for(int i=0; i<vertexCount; i++){
            
            //el vértice actual se selecciona en base a un desfase con el 
            //vértice inicial 
            int u = (i+initialVertex)%vertexCount;
            
            //Deben elegirse solamente aristas cuyos vértices no han sido 
            //visitados
            if(visited[u] == false){
                
                for(int j=0; j<adyacentVertices[u].size();j++){
                    int v = adyacentVertices[u].get(j);
                    if(visited[v] == false){
                        visited[u] = true;
                        visited[v] = true;
                        vertexCoverList.add(u);
                        vertexCoverList.add(v);
                        break;
                    }
                }
            }
        }
        return vertexCoverList;
    }
    
    /**
     * Método no-óptimo para resolver el problema de vertex cover
     * @param g
     * @return la combinación de vértices que genera un vertex cover mínimo
     */
    public static int[] vertexCoverNoOptimo(Grafo g){
             
        //El número de vertices se utiliza para controlas varias iteraciones
        int vertexCount = g.getVertexCount();
      
        //Se trae la lista de adyacencia del grafo g
        LinkedList<Integer> adjacentVertices[] = g.getAdjacentVertices();  
        
        //Esta porción de código va probando combinaciones de vértices iniciando
        //iniciando con un r = 1 (combinaciones de tamaño 1) hasta 
        //r = numero de vertices - 1
        for(int r=1; r<vertexCount-1; r++){
            
            //Este es el arreglo que contendrá una combinación en particular
            int[] combinacion = new int[r];
      
            //Se inicializa el arreglo con la combinación lexicografica 
            //más pequeña
            for (int i = 0; i < r; i++) {
                combinacion[i] = i;
            }
            
            //Este while se encarga de generar todas las combinaciones de tamaño r
            while (combinacion[r - 1] < vertexCount) {
                                
                //Se crea e inicializa una copia de la lista de vértices 
                //adyacentes para manipular
                LinkedList<Integer>[] adjacentVerticesCopy = new LinkedList[vertexCount];
                for(int i=0; i<vertexCount; i++)
                    adjacentVerticesCopy[i] = new LinkedList();
                
                //Se copian todos los elementos de adjacentVertices a 
                //adjacentVerticesCopy
                for (int i = 0; i < vertexCount; i++) {
                    for (int j = 0; j < adjacentVertices[i].size(); j++) {
                        adjacentVerticesCopy[i].add(adjacentVertices[i].get(j));
                    }
                }
                
                //Se prueba la combinación de vértices y se retorna la misma si resulta
                //ser un vertex cover completo
                if(probarVertexCover(combinacion.clone(), adjacentVerticesCopy))
                    return combinacion.clone();
                
                //Se genera la siguiente combinación
                int t = r - 1;
                while (t != 0 && combinacion[t] == vertexCount - r + t) {
                    t--;
                }
                combinacion[t]++;
                for (int i = t + 1; i < r; i++) {
                    combinacion[i] = combinacion[i - 1] + 1;
                }
            }

        }
        return new int[1];
    }
    
    /**
     * Mérodo para comprobar si el conjunto de vértices cubre todas las aristas
     * @param verticesDePrueba
     * @param adyacentVertices
     * @return true si el conjunto de vértices cubre a todas las aristas
     *         false si ocurre lo contrario
     */
    public static boolean probarVertexCover(
            int[] verticesDePrueba,
            LinkedList<Integer>[] adyacentVertices
    ){
        
        //Se hace un conteo total del número de aristas (el cual resulta en el
        //doble debido a que es un grafo no-dirigido)
        int numeroDeAristas = 0;
        for (int i = 0; i < adyacentVertices.length; i++) {
            numeroDeAristas+=adyacentVertices[i].size();
        }  
        
        //El contador de aristas cubiertas para corroborar si se cubrieron todas
        int aristasCubiertas = 0;
        
        //For para recorrer los vértices de prueba
        for (int i = 0; i < verticesDePrueba.length; i++) {
            
            //For para recorrer la lista de adyacencia por vértice del grafo
            for (int j = 0; j < adyacentVertices.length; j++) {  
                
                //For para recorrer los vértices adyacentes a cada vértice del grafo
                for (int k = 0; k < adyacentVertices[j].size(); k++) {
                    
                    //Este bloque if-else se encarga de probar dos casos para
                    //ver si se cubre determinada arista:
                    //      1)el vértice del conjunto de prueba es igual al
                    //        vértice cuyas aristas se están recorriendo en 
                    //        la lista de adyacencia y no se ha contado antes
                    //      2)el vértice de prueba no es igual al vértice cuyas
                    //        aristas se están recorriendo en la lista de 
                    //        adyacencia, pero si es igual al vértice al cual 
                    //        conecta determinada arista (y no ha sido visitado)
                    if(verticesDePrueba[i]==j && adyacentVertices[j].get(k) != -1){
                        adyacentVertices[j].set(k, -1);
                        aristasCubiertas++;
                    }else if(verticesDePrueba[i]!=j && adyacentVertices[j].get(k)==verticesDePrueba[i]){
                        adyacentVertices[j].set(k, -1);
                        aristasCubiertas++;
                    }
                }
            }
        }
                
        return numeroDeAristas==aristasCubiertas;
    }
}