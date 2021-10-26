package ar.edu.itba.pod.query4;

import ar.edu.itba.pod.model.Pair;
import com.hazelcast.mapreduce.Collator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query4Collator implements Collator<Map.Entry<String, Integer>, List<Query4Result>> {

    @Override
    public List<Query4Result> collate(Iterable<Map.Entry<String, Integer>> iterable) {
        Map<Integer, List<String>> hundredsOfSpecies = new HashMap<>();
        List<Query4Result> results = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : iterable) {
            int speciesAmountInHundreds = entry.getValue() / 100;
            if(!hundredsOfSpecies.containsKey(speciesAmountInHundreds)){
                hundredsOfSpecies.put(speciesAmountInHundreds, new ArrayList<>());
            }
            for(String neighbour : hundredsOfSpecies.get(speciesAmountInHundreds)) {
                results.add(new Query4Result(speciesAmountInHundreds, neighbour, entry.getKey()));
            }
            hundredsOfSpecies.get(speciesAmountInHundreds).add(entry.getKey());
        }
        return results;
    }
}
