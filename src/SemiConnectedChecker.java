

import java.util.*;

public class SemiConnectedChecker {

    private int totalVertices;
    private Map<Integer, List<Integer>> graph = new HashMap<>();

    public SemiConnectedChecker(int totalVertices) {
        this.totalVertices = totalVertices;
    }

    public void addEdge(int u, int v) {
        graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
    }

    public boolean isSemiConnected() {
        boolean[] visited = new boolean[totalVertices];
        Stack<Integer> finishStack = new Stack<>();
        for (int i = 0; i < totalVertices; i++) {
            if (!visited[i]) dfs(i, visited, finishStack, graph);
        }

        Map<Integer, List<Integer>> reversed = reverseGraph();

        Arrays.fill(visited, false);
        List<List<Integer>> sccList = new ArrayList<>();
        while (!finishStack.isEmpty()) {
            int node = finishStack.pop();
            if (!visited[node]) {
                List<Integer> component = new ArrayList<>();
                dfsCollect(node, visited, reversed, component);
                sccList.add(component);
            }
        }

        Map<Integer, Set<Integer>> sccGraph = new HashMap<>();
        Map<Integer, Integer> nodeToSCC = new HashMap<>();
        for (int i = 0; i < sccList.size(); i++) {
            for (int node : sccList.get(i)) {
                nodeToSCC.put(node, i);
            }
        }

        for (int u = 0; u < totalVertices; u++) {
            for (int v : graph.getOrDefault(u, new ArrayList<>())) {
                int su = nodeToSCC.get(u);
                int sv = nodeToSCC.get(v);
                if (su != sv) {
                    sccGraph.computeIfAbsent(su, k -> new HashSet<>()).add(sv);
                }
            }
        }

        List<Integer> topoOrder = topoSort(sccGraph, sccList.size());

        for (int i = 0; i < topoOrder.size() - 1; i++) {
            int from = topoOrder.get(i);
            int to = topoOrder.get(i + 1);
            if (!sccGraph.getOrDefault(from, new HashSet<>()).contains(to)) {
                return false;
            }
        }

        return true;
    }

    private void dfs(int node, boolean[] visited, Stack<Integer> stack, Map<Integer, List<Integer>> g) {
        visited[node] = true;
        for (int neighbor : g.getOrDefault(node, new ArrayList<>())) {
            if (!visited[neighbor]) dfs(neighbor, visited, stack, g);
        }
        stack.push(node);
    }

    private void dfsCollect(int node, boolean[] visited, Map<Integer, List<Integer>> g, List<Integer> component) {
        visited[node] = true;
        component.add(node);
        for (int neighbor : g.getOrDefault(node, new ArrayList<>())) {
            if (!visited[neighbor]) dfsCollect(neighbor, visited, g, component);
        }
    }

    private Map<Integer, List<Integer>> reverseGraph() {
        Map<Integer, List<Integer>> reversed = new HashMap<>();
        for (int u = 0; u < totalVertices; u++) {
            for (int v : graph.getOrDefault(u, new ArrayList<>())) {
                reversed.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
            }
        }
        return reversed;
    }

    private List<Integer> topoSort(Map<Integer, Set<Integer>> g, int numNodes) {
        int[] inDegree = new int[numNodes];
        for (int u : g.keySet()) {
            for (int v : g.get(u)) {
                inDegree[v]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numNodes; i++) {
            if (inDegree[i] == 0) queue.add(i);
        }

        List<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            order.add(u);
            for (int v : g.getOrDefault(u, new HashSet<>())) {
                inDegree[v]--;
                if (inDegree[v] == 0) queue.add(v);
            }
        }

        return order;
    }
}

