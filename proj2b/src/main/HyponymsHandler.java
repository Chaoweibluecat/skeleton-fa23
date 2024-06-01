package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    WordNet graph;

    NGramMap map;
    public HyponymsHandler(WordNet graph, NGramMap map) {
        this.graph = graph;
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        Set<String> res = new TreeSet<>(graph.hyponyms(q.words().get(0)));
        for (int i = 1; i < q.words().size(); i++) {
            TreeSet<String> hyponyms = graph.hyponyms(q.words().get(i));
            res.removeIf(word -> !hyponyms.contains(word));
        }
        Comparator<String> comparator = new Comparator<>() {
            @Override
            public int compare(String o1, String o2) {
                double diff = (sumUp(map.countHistory(o1, q.startYear(), q.endYear())) -
                        sumUp(map.countHistory(o2, q.startYear(), q.endYear())));
                return diff > 0.0 ? 1 : -1;
            }
        };
        // 创建k最小堆,top-K solution
        if (q.k() != 0) {
            PriorityQueue<String> pq = new PriorityQueue<>(comparator);
            for (String word : res) {
                if (pq.size() < q.k()) {
                    pq.add(word);
                } else {
                    if (comparator.compare(word, pq.peek()) > 0) {
                        pq.poll();
                        pq.add(word);
                    }
                }
            }
            res = new HashSet<>(pq);
        }
        return res.toString();
    }

    public Double sumUp(TimeSeries timeSeries) {
        return timeSeries.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    }
