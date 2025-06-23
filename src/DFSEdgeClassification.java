import java.util.*;

public class DFSEdgeClassification {

    enum EdgeType { TREE, BACK, FORWARD, CROSS }

    static class Graph {
        Map<String, List<String>> adj = new HashMap<>();
        Map<String, Integer> d = new HashMap<>();
        Map<String, Integer> f = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        Map<String, String> color = new HashMap<>();
        List<String[]> edges = new ArrayList<>();
        int time = 0;

        public void addEdge(String u, String v) {
            adj.putIfAbsent(u, new ArrayList<>());
            adj.get(u).add(v);
            edges.add(new String[]{u, v});
        }

        public void dfs() {
            for (String u : adj.keySet()) {
                color.put(u, "WHITE");
                parent.put(u, null);
            }
            time = 0;
            for (String u : adj.keySet()) {
                if (color.get(u).equals("WHITE")) {
                    dfsVisit(u);
                }
            }
        }

        private void dfsVisit(String u) {
            color.put(u, "GRAY");
            time++;
            d.put(u, time);

            for (String v : adj.getOrDefault(u, List.of())) {
                if (color.get(v).equals("WHITE")) {
                    parent.put(v, u);
                    dfsVisit(v);
                }
            }

            color.put(u, "BLACK");
            time++;
            f.put(u, time);
        }

        public void classifyEdges() {
            for (String[] edge : edges) {
                String u = edge[0], v = edge[1];
                if (parent.get(v) != null && parent.get(v).equals(u)) {
                    System.out.printf("(%s → %s): קשת עץ (TREE)%n", u, v);
                } else if (d.get(v) != null && d.get(v) < d.get(u) && f.get(u) < f.get(v)) {
                    System.out.printf("(%s → %s): קשת אחורה (BACK)%n", u, v);
                } else if (d.get(u) < d.get(v)) {
                    System.out.printf("(%s → %s): קשת קדימה (FORWARD)%n", u, v);
                } else {
                    System.out.printf("(%s → %s): קשת חוצה (CROSS)%n", u, v);
                }
            }
        }
    }

    public static void run() {
        Graph g = new Graph();

        // הקשתות לפי התרשים
        g.addEdge("s", "z");
        g.addEdge("s", "w");
        g.addEdge("z", "y");
        g.addEdge("z", "w");
        g.addEdge("y", "x");
        g.addEdge("x", "z");
        g.addEdge("w", "x");
        g.addEdge("v", "s");
        g.addEdge("v", "w");
        g.addEdge("t", "v");
        g.addEdge("t", "u");
        g.addEdge("u", "t");
        g.addEdge("u", "v");


        g.dfs();
        g.classifyEdges();

        // הצגת זמני d ו־f
        System.out.println("\nזמני גילוי וסיום:");
        for (String v : g.d.keySet()) {
            System.out.printf("%s: d=%d, f=%d%n", v, g.d.get(v), g.f.get(v));
        }
    }
}