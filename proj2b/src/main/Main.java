package main;

import browser.NgordnetServer;
import ngrams.NGramMap;

public class Main {
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();


        String wordFile = "././proj2a/data/ngrams/top_14377_words.csv";
        String countFile = "././proj2a/data/ngrams/total_counts.csv";
        String synsetFile = "././proj2a/data/wordnet/synsets16.txt";
        String hymFile = "././proj2a/data/wordnet/hyponyms16.txt";
        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNet graph = new WordNet(synsetFile, hymFile);


        hns.startUp();
        hns.register("history", new HistoryHandler(ngm));
        hns.register("historytext", new HistoryTextHandler(ngm));
        hns.register("hyponyms", new HyponymsHandler(graph, ngm));

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet_2a.html");
    }
}
