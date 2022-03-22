package graph;
import java.util.ArrayList;


/**
 * This class implements general operations on a graph as specified by UndirectedGraphADT.
 * It implements a graph where data is contained in Vertex class instances.
 * Edges between verticies are unweighted and undirected.
 * A graph coloring algorithm determines the chromatic number. 
 * Colors are represented by integers. 
 * The maximum number of vertices and colors must be specified when the graph is instantiated.
 * You may implement the graph in the manner you choose. See instructions and course material for background.
 */
 
 public class UndirectedUnweightedGraph<T> implements UndirectedGraphADT<T>{
   // private class variables here.
   
   private int MAX_VERTICES;
   private int MAX_COLORS;
    // TODO: Declare class variables here.
   private int adjMat[][];
   private Vertex vertexList[];
   private int numVert;
   private int numEdges;

   
   /**
    * Initialize all class variables and data structures. 
   */   
   public UndirectedUnweightedGraph (int maxVertices, int maxColors){
      MAX_VERTICES = maxVertices;
      MAX_COLORS = maxColors; 
     // TODO: Implement the rest of this method.
      adjMat = new int[maxVertices][maxVertices];
      for(int i=0; i<maxVertices; i++){
        for(int j=0; j<maxVertices; j++){
          adjMat[i][j] = 0;
        }
      }
      numVert = 0;
      numEdges = 0;
      vertexList = new Vertex[maxVertices];
   }

   /**
    * Add a vertex containing this data to the graph.
    * Throws Exception if trying to add more than the max number of vertices.
   */
   public void addVertex(T data) throws Exception {
    // TODO: Implement this method.
    if(numVert>=MAX_VERTICES){
      throw new Exception();
    }
    numVert++;
    int i=0;
    while(vertexList[i]!=null){
      i++;
    }
    vertexList[i] = new Vertex<T>(data);
   }
   
   /**
    * Return true if the graph contains a vertex with this data, false otherwise.
   */   
   public boolean hasVertex(T data){
    // TODO: Implement this method.
      boolean returnThis = false;

      for(int i=0; i<MAX_VERTICES; i++){
        if(vertexList[i] != null && vertexList[i].data.equals(data) ){
          return true;
        }
      }
      return returnThis;
   } 

   /**
    * Add an edge between the vertices that contain these data.
    * Throws Exception if one or both vertices do not exist.
   */   
   public void addEdge(T data1, T data2) throws Exception{
    // TODO: Implement this method.
    if(!hasVertex(data1) || !hasVertex(data2)){
      throw new Exception();
    }
    numEdges++;
    adjMat[getVertex(data1)][getVertex(data2)] = 1;
    adjMat[getVertex(data2)][getVertex(data1)] = 1;
   }

   /**
    * Get an ArrayList of the data contained in all vertices adjacent to the vertex that
    * contains the data passed in. Returns an ArrayList of zero length if no adjacencies exist in the graph.
    * Throws Exception if a vertex containing the data passed in does not exist.
   */   
   public ArrayList<T> getAdjacentData(T data) throws Exception{
    // TODO: Implement this method.
    if(!hasVertex(data)){
      throw new Exception();
    }
    ArrayList<T> arrList = new ArrayList<T>();
    int j = getVertex(data);
    for(int i=0; i<MAX_VERTICES; i++){
      if(adjMat[j][i]==1){
        arrList.add((T)vertexList[i].data);
      }
    }
    return arrList;
   }
   
   private int getVertex(T data){
    for(int i=0; i<MAX_VERTICES; i++){
      if(vertexList[i] != null && vertexList[i].data.equals(data) ){
        return i;
      }
    }
    return -1;
   }
   /**
    * Returns the total number of vertices in the graph.
   */   
   public int getNumVertices(){
    // TODO: Implement this method.
      return numVert;
   }

   /**
    * Returns the total number of edges in the graph.
   */   
   public int getNumEdges(){
    // TODO: Implement this method.
      return numEdges;
   }

   /**
    * Returns the minimum number of colors required for this graph as 
    * determined by a graph coloring algorithm.
    * Throws an Exception if more than the maximum number of colors are required
    * to color this graph.
   */   
    public int getChromaticNumber() throws Exception{
    // TODO: Implement this method.
      
      int highestColorUsed = -1;
      int colorToUse = -1;
      for(int i=0; i<MAX_VERTICES ;i++){
        if(vertexList[i]!=null && vertexList[i].color==-1){
          colorToUse = getColorToUse(vertexList[i]);
          vertexList[i].setColor(colorToUse);
          if(colorToUse>highestColorUsed){
            highestColorUsed = colorToUse;
          }
        }
      }

      return highestColorUsed+1;
    }
    
    private int getColorToUse(Vertex curVertex) throws Exception{
      int colorToUse = -1;
      int tempCount=0;
      boolean[] adjColorsUsed = new boolean[MAX_COLORS];
      ArrayList<Vertex> adjVertsList = getAdjacentVertices(curVertex);
      for(int i=0; i < adjVertsList.size(); i++){
        if(adjVertsList.get(i).color > -1){
          adjColorsUsed[adjVertsList.get(i).color] = true;
        }
      }
      for(int i=0; i<MAX_COLORS; i++){
        if(!adjColorsUsed[i]){
          return i;
        }
        else{
          tempCount++;
        }
      }
      
      throw new Exception();
      
    }
    private ArrayList<Vertex> getAdjacentVertices(Vertex curVertex){
    ArrayList<Vertex> arrList = new ArrayList<Vertex>();
    int j = getVertex((T)curVertex.data);
    for(int i=0; i<MAX_VERTICES; i++){
      if(adjMat[j][i]==1){
        arrList.add(vertexList[i]);
      }
    }
    return arrList;
    }
   
}