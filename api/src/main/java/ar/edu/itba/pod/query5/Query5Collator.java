package ar.edu.itba.pod.query5;

import ar.edu.itba.pod.model.Pair;
import ar.edu.itba.pod.model.ThreeGroup;
import ar.edu.itba.pod.query4.Query4Result;
import com.hazelcast.mapreduce.Collator;

import java.util.*;
import java.util.stream.Collectors;

public class Query5Collator implements Collator<Map.Entry<String, Integer>, List<Query5Result>> {

    final Comparator<Query5Result> comparator = (q1,q2) ->{
        int tensComparison = q2.getTensOfSpecies() - q1.getTensOfSpecies();
        if(tensComparison != 0)
            return tensComparison;
        else return q1.getFirstStreet().compareTo(q2.getFirstStreet());
    };

    @Override
    public List<Query5Result> collate(Iterable<Map.Entry<String, Integer>> iterable) {
        Map<Integer, List<String>> tensToStreetList = new HashMap<>();
        List<Query5Result> results = new ArrayList<>();

        for(Map.Entry<String,Integer> entry : iterable){
            int tens = entry.getValue() / 10;
            if(tens > 0) {
                if (!tensToStreetList.containsKey(tens))
                    tensToStreetList.put(tens, new ArrayList<>());

                for(String street:tensToStreetList.get(tens)){
                    results.add(new Query5Result(tens,entry.getKey(),street));
                }
                tensToStreetList.get(tens).add(entry.getKey());
            }
        }
        return results.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
