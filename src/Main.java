public class Main {
    public static void main(String[] args) {

//שאלה 1
        System.out.println("סיווג קשתות ב־DFS:");
        DFSEdgeClassification.run();


        // שאלה 2
        int numVertices = 8;
        SemiConnectedChecker checker = new SemiConnectedChecker(numVertices);

        checker.addEdge(3, 7); // s → z
        checker.addEdge(3, 4); // s → w
        checker.addEdge(7, 6); // z → y
        checker.addEdge(7, 4); // z → w
        checker.addEdge(6, 5); // y → x
        checker.addEdge(5, 7); // x → z
        checker.addEdge(4, 5); // w → x
        checker.addEdge(2, 3); // v → s
        checker.addEdge(2, 4); // v → w
        checker.addEdge(0, 2); // t → v
        checker.addEdge(0, 1); // t → u
        checker.addEdge(1, 0); // u → t
        checker.addEdge(1, 2); // u → v

        System.out.println("האם הגרף semi-connected?");
        boolean result = checker.isSemiConnected();
        System.out.println(result ? "כן " : "לא ");

    }
}
