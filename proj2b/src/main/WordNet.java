package main;

import edu.princeton.cs.algs4.In;

import java.util.*;
import java.util.stream.Collectors;

public class WordNet {
    public TreeMap<Integer, GraphNode> nodes = new TreeMap<>();

    public static class GraphNode{
        int id;
        final List<String> syms;
        final List<Integer> hyps = new ArrayList<>();

        public GraphNode(int id, List<String> input) {
            this.id = id;
            syms = new ArrayList<>(input);
        }
        public void addHyp(Integer hym){
            hyps.add(hym);
        }
    }
    public WordNet(String synSets, String hyponyms){
        In in = new In(synSets);
        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            List<String> inputs = Arrays.stream(splitLine[1].split(" ")).collect(Collectors.toList());
            int id = Integer.parseInt(splitLine[0]);
            nodes.put(id, new GraphNode(id, inputs));
        }

        in = new In(hyponyms);
        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            int parentId = Integer.parseInt(splitLine[0]);
            for (int i = 1; i < splitLine.length; i++) {
                nodes.get(parentId).addHyp(Integer.parseInt(splitLine[i]));
            }
        }
        System.out.println(1);
    }

    public TreeSet<String> hyponyms(String base) {
        Set<Integer> visited = new HashSet<>();
        TreeSet<String> res = new TreeSet<>();
        for (Map.Entry<Integer, GraphNode> entry : nodes.entrySet()) {
            dfsToFindHyp(entry.getValue(), visited, base, false, res);
        }
        return res;
    }

    private void dfsToFindHyp(GraphNode graphNode,
                              Set<Integer> visited,
                              final String target,
                              final boolean prevMatch,
                              final Set<String> result) {
        if (visited.contains(graphNode.id)) {
            return;
        }
        visited.add(graphNode.id);
        boolean match = prevMatch;
        if (graphNode.syms.contains(target)) {
            match = true;
        }
        if (match) {
            result.addAll(graphNode.syms);
        }
        for (Integer childId : graphNode.hyps) {
            dfsToFindHyp(nodes.get(childId), visited, target, match, result);
        }
    }
}
