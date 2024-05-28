package main;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

class WordNetTest {

    @Test
    public void test1(){
        WordNet wn=new WordNet("././proj2a/data/wordnet/synsets11.txt","././proj2a/data/wordnet/hyponyms11.txt");
        Set<String> antihistamine = wn.hyponyms("antihistamine");
        assertThat(wn.hyponyms("antihistamine")).isEqualTo(Set.of("antihistamine","actifed"));

    }

}