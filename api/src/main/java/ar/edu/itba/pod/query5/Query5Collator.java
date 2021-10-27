package ar.edu.itba.pod.query5;

import ar.edu.itba.pod.model.ThreeGroup;
import ar.edu.itba.pod.query4.Query4Result;
import com.hazelcast.mapreduce.Collator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query5Collator implements Collator<Map.Entry<ThreeGroup<String, String, String>, Integer>, List<Query5Result>> {

    @Override
    public List<Query5Result> collate(Iterable<Map.Entry<ThreeGroup<String, String, String>, Integer>> iterable) {
        List<Query5Result> results = new ArrayList<>();
        Map<Integer, List<ThreeGroup<String, String, String>>> tensOfSpecies = new HashMap<>();
        for(Map.Entry<ThreeGroup<String, String, String>, Integer> entry : iterable){
            int speciesAmountInTens = entry.getValue() / 10;
            if(!tensOfSpecies.containsKey(speciesAmountInTens)){
                tensOfSpecies.put(speciesAmountInTens, new ArrayList<>());
            }
            for(ThreeGroup<String, String, String> group : tensOfSpecies.get(speciesAmountInTens)){
                if(group.equalsLeft(entry.getKey().getLeft()) && group.equalsRight(entry.getKey().getRight())) {
                    String streetA = group.getMiddle();
                    String streetB = entry.getKey().getMiddle();
                    if (streetA.compareTo(streetB) > 0) //Pongo las calles alfabeticamente
                        results.add(new Query5Result(speciesAmountInTens, group.getLeft(), group.getRight(), streetA, streetB));
                    else
                        results.add(new Query5Result(speciesAmountInTens, group.getLeft(), group.getRight(), streetB, streetA));
                }
            }
            tensOfSpecies.get(speciesAmountInTens).add(entry.getKey());
        }
        return results;
    }
}