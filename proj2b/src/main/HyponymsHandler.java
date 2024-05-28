package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;
import java.util.TreeSet;

public class HyponymsHandler extends NgordnetQueryHandler {
    WordNet graph;
    public HyponymsHandler(WordNet graph) {
        this.graph = graph;
    }

    @Override
    public String handle(NgordnetQuery q) {
        TreeSet<String> res = new TreeSet<>(graph.hyponyms(q.words().get(0)));
        for (int i = 1; i < q.words().size(); i++) {
            TreeSet<String> hyponyms = graph.hyponyms(q.words().get(i));
            res.removeIf(word -> !hyponyms.contains(word));
        }
        return res.toString();
    }
}
