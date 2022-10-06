import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BetterDiGraph implements EditableDiGraph{
    private HashMap<Integer, HashSet<Integer>> adjacency_map;
    private int num_vertices;
    private int num_edges;

    public BetterDiGraph(){
        this.adjacency_map = new HashMap<>();
    }

    @Override
    public void addEdge(int v, int w) {
        addVertex(v);
        addVertex(w);

        if(this.adjacency_map.containsKey(v)){
            if(!this.adjacency_map.get(v).contains(w)){
                this.adjacency_map.get(v).add(w);
                this.num_edges++;
            }
        }
    }

    @Override
    public void addVertex(int v) {
        if (!this.adjacency_map.containsKey(v)) {
            this.adjacency_map.put(v, new HashSet<>());
            this.num_vertices++;
        }
    }

    @Override
    public Iterable<Integer> getAdj(int v) {
        return this.adjacency_map.get(v);
    }

    @Override
    public int getEdgeCount() {
        return num_edges;
    }

    @Override
    public int getIndegree(int v) throws NoSuchElementException {
        List<Integer> in_list = new ArrayList<>();

        if(!containsVertex(v)){
            throw new NoSuchElementException();
        }
        for(Integer i : this.adjacency_map.keySet()){
            for (Integer j : this.adjacency_map.get(i)){
                if(i == v){
                    in_list.add(j);
                }
            }
        }
        return in_list.size();
    }

    @Override
    public int getVertexCount() {
        return num_vertices;
    }

    @Override
    public void removeEdge(int v, int w) {
        if (this.adjacency_map.containsKey(v) && this.adjacency_map.containsKey(w)) {
            if (this.adjacency_map.get(v).contains(w)) {
                this.adjacency_map.get(v).remove(w);
                this.num_edges--;
            }
        }
    }

    @Override
    public void removeVertex(int v) {
        if (this.adjacency_map.containsKey(v)) {
            this.adjacency_map.remove(v);
            for (Map.Entry<Integer, HashSet<Integer>> entry : this.adjacency_map.entrySet()) {
                if (entry.getValue().contains(v)) {
                    this.adjacency_map.get(entry.getKey()).remove(v);
                }
            }
            this.num_vertices--;
        }
    }

    @Override
    public Iterable<Integer> vertices() {
        List<Integer> iter = new ArrayList<>();

        for (Integer to : this.adjacency_map.keySet()) {
            if (!iter.contains(to))
                iter.add(to);
        }
        return iter;
    }

    @Override
    public boolean isEmpty() {
        return this.num_vertices == 0;
    }

    @Override
    public boolean containsVertex(int v) {
        if (this.adjacency_map.containsKey(v)) {
            return true;
        }
        return false;
    }
}
