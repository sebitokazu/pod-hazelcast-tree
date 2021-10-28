package ar.edu.itba.pod.query4;

import ar.edu.itba.pod.model.Pair;
import com.hazelcast.mapreduce.Collator;

import java.util.*;
import java.util.stream.Collectors;

public class Query4Collator implements Collator<Map.Entry<String, Integer>, List<Query4Result>> {

    final Comparator<Query4Result> comparator = new Comparator<Query4Result>() {
        @Override
        public int compare(Query4Result o1, Query4Result o2) {
            int groupComparison = o2.getHundredsOfSpecies() - o1.getHundredsOfSpecies();
            if(groupComparison != 0) {
                return groupComparison;
            }
            else {
                int firstNeighborComparator = o1.getFirstNeighbour().compareTo(o2.getFirstNeighbour());
                if(firstNeighborComparator != 0)
                    return firstNeighborComparator;
                else return o1.getSecondNeighbour().compareTo(o2.getSecondNeighbour());
            }
        }
    };

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
                results.add(new Query4Result(speciesAmountInHundreds*100, entry.getKey(), neighbour));
            }
            hundredsOfSpecies.get(speciesAmountInHundreds).add(entry.getKey());
        }
        return results.stream().sorted(comparator).collect(Collectors.toList());
    }
}
