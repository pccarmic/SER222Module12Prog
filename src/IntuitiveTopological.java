import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IntuitiveTopological implements TopologicalSort{
    private BetterDiGraph digraph;

    public IntuitiveTopological(BetterDiGraph digraph){
        this.digraph = digraph;
    }

    @Override
    public Iterable<Integer> order() {
        List<Integer> iter = new ArrayList<>();
        if (isDAG()) {
            while (this.digraph.getVertexCount() != 0) {
                for (Integer v : this.digraph.vertices()) {
                    if (this.digraph.getIndegree(v) == 0) {
                        iter.add(v);
                        this.digraph.removeVertex(v);
                    }
                }
            }
        }
        return iter;
    }

    @Override
    public boolean isDAG() {
        return !isCyclic();
    }

    private boolean isCyclic() {
        Map<Integer, Boolean> visited = new HashMap<Integer, Boolean>();
        Map<Integer, Boolean> cycle_stack = new HashMap<Integer, Boolean>();
        for (Integer v : this.digraph.vertices()) {
            cycle_stack.put(v, false);
            visited.put(v, false);
        }

        for (Integer v : this.digraph.vertices()) {
            if (isCyclicUtil(v, visited, cycle_stack)) {
                return true;
            }
        }
        return false;
    }
    private boolean isCyclicUtil(int i, Map<Integer, Boolean> visited, Map<Integer, Boolean> cycle_stack) {
        if (cycle_stack.get(i)) {
            return true;
        }
        if (visited.get(i)) {
            return false;
        }

        visited.put(i, true);
        cycle_stack.put(i, true);
        Iterable<Integer> children = this.digraph.getAdj(i);
        for (Integer c : children) {
            if (isCyclicUtil(c, visited, cycle_stack)) {
                return true;
            }
        }
        cycle_stack.put(i, false);

        return false;
    }
}
