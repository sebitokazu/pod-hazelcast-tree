package ar.edu.itba.pod.query2;

import ar.edu.itba.pod.model.Pair;
import com.hazelcast.mapreduce.Collator;

import java.util.*;
import java.util.stream.Collectors;

public class Query2Collator implements Collator<Map.Entry<String, Pair<String,Integer>>, List<Pair<String,Pair<String,Double>>>> {

    private final Map<String,Integer> neighbourhoodHabitantsMap;

    public Query2Collator(Map<String,Integer> neighbourhoodHabitantsMap){
        this.neighbourhoodHabitantsMap = neighbourhoodHabitantsMap;
    }

    @Override
    public List<Pair<String, Pair<String, Double>>> collate(Iterable<Map.Entry<String, Pair<String, Integer>>> iterable) {
        //Lista para guardar la tupla de barrio,arbol,cantidad_arboles
        List<Pair<String, Pair<String, Integer>>> nonRatioList = new ArrayList<>();
        Iterator<Map.Entry<String,Pair<String,Integer>>> iterator = iterable.iterator();
        Map.Entry<String,Pair<String,Integer>> entry;
        while (iterator.hasNext()){
            entry = iterator.next();
            nonRatioList.add(new Pair<>(entry.getKey(), entry.getValue()));
        }

        //ordeno la lista alfabetico por barrio y mapeo a una tupla de barrio,arbol, cantidad_arboles/cantidad_habitantes de barrio
        return nonRatioList.stream()
                .sorted(Comparator.comparing(Pair::getLeft))
                .map(pair-> new Pair<>(
                        pair.getLeft(),
                        new Pair<>(pair.getRight().getLeft(), pair.getRight().getRight()*1.0 / neighbourhoodHabitantsMap.get(pair.getLeft()))))
                .collect(Collectors.toList());
    }
}
