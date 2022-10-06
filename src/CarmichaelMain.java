import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Program for generating kanji component dependency order via topological sort.
 * 
 * @author Paul Carmichael, Acuna
 * @version 1.0
 */
public class CarmichaelMain {
    
    /**
     * Entry point for testing.
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //Freebie: this is one way to load the UTF8 formatted character data.
        BufferedReader indexReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data-kanji.txt")), "UTF8"));

        HashMap<Integer, String> input_hash = new HashMap<>();
        BetterDiGraph graph = new BetterDiGraph();
        String input_string;
        int i = 0;

        while((input_string = indexReader.readLine()) != null){
            int key = 0;
            String symbol = null;

            if(input_string.charAt(0) == '#' || i < 1){
                i++;
            } else if(input_string.charAt(0) != '#') {
                String[] split = input_string.split("\\s");
                try {
                    key = Integer.parseInt(split[0]);
                } catch (NumberFormatException exception){
                    key = 0;
                }
                symbol = split[1];

                input_hash.put(key, symbol);
                graph.addVertex(key);
            }
        }


        BufferedReader edgeReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data-components.txt")), "UTF8"));
        int j = 0;

        while((input_string = edgeReader.readLine()) != null){
            int v = 0;
            int w = 0;

            if(input_string.charAt(0) == '#' || j < 1){
                i++;
            } else if(input_string.charAt(0) != '#') {
                String[] split = input_string.split("\\s");
                try {
                    v = Integer.parseInt(split[0]);
                    w = Integer.parseInt(split[1]);
                } catch (NumberFormatException exception){
                    v = 0;
                    w = 0;
                }
                graph.addEdge(v, w);
            }
        }
        IntuitiveTopological topo_graph = new IntuitiveTopological(graph);


        for(Integer key : topo_graph.order()) {
            System.out.print(input_hash.get(key) + " ");
        }

        /*System.out.println("\n\t  Edge Count: " + graph.getEdgeCount());
        System.out.println("\tVertex Count: " + graph.getVertexCount());
        System.out.println("\t\t  Is DAG: " + topo_graph.isDAG());
        System.out.println(" Contains vertex: " + graph.containsVertex(76));

        //test removing edges
        graph.removeEdge(106,20);
        graph.removeEdge(106,80);
        graph.removeEdge(116,93);
        graph.removeEdge(99,93);

        //test removing vertices
        graph.removeVertex(76);
        graph.removeVertex(59);
        graph.removeVertex(120);
        graph.removeVertex(114);
        graph.removeVertex(87);
        graph.removeVertex(15);


        System.out.println("\n\t  Edge Count: " + graph.getEdgeCount());
        System.out.println("\tVertex Count: " + graph.getVertexCount());
        System.out.println("\t\t  Is DAG: " + topo_graph.isDAG());
        System.out.println(" Contains vertex: " + graph.containsVertex(76));
        System.out.println("   Get inDegrees: " + graph.getIndegree(99));

        //test edge case of 0 vertices
        BetterDiGraph test_empty_graph = new BetterDiGraph();
        System.out.println("\nTest Empty Graph: " + test_empty_graph.isEmpty());
        System.out.println("CheckEmpty Graph: " + graph.isEmpty());*/

    }
}