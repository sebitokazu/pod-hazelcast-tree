package ar.edu.itba.pod.query5;

import ar.edu.itba.pod.model.Pair;
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
        Map<Integer, Map<String, List<String>>> tensOfSpecies = new HashMap<>();
        for(Map.Entry<ThreeGroup<String, String, String>, Integer> entry : iterable){
            String currentNeighbour = entry.getKey().getLeft();
            String currentStreet = entry.getKey().getMiddle();
            int speciesAmountInTens = entry.getValue() / 10;
            if(!tensOfSpecies.containsKey(speciesAmountInTens)){
                tensOfSpecies.put(speciesAmountInTens, new HashMap<>());
                tensOfSpecies.get(speciesAmountInTens).put(entry.getKey().getLeft(), new ArrayList<>());
            }
            else{
                if(!tensOfSpecies.get(speciesAmountInTens).containsKey(currentNeighbour)){
                    tensOfSpecies.get(speciesAmountInTens).put(currentNeighbour, new ArrayList<>());
                }
            }
            for(String street : tensOfSpecies.get(speciesAmountInTens).get(currentNeighbour)){
                results.add(new Query5Result(speciesAmountInTens, street, currentStreet));
            }
            tensOfSpecies.get(speciesAmountInTens).get(currentNeighbour).add(currentStreet);
        }
        return results;
    }
}
