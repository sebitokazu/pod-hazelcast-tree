package ar.edu.itba.pod.query2;

import ar.edu.itba.pod.model.Neighbourhood;
import ar.edu.itba.pod.model.Pair;
import ar.edu.itba.pod.model.ThreeGroup;
import com.hazelcast.mapreduce.Collator;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Query2Collator implements Collator<Map.Entry<String, Pair<String,Integer>>, List<ThreeGroup<String,String,Double>>> {

    private final Map<String, Neighbourhood> neighbourhoodHabitantsMap;

    public Query2Collator(Map<String,Neighbourhood> neighbourhoodHabitantsMap){
        this.neighbourhoodHabitantsMap = neighbourhoodHabitantsMap;
    }

    @Override
    public List<ThreeGroup<String, String, Double>> collate(Iterable<Map.Entry<String, Pair<String, Integer>>> iterable) {
        //Lista para guardar la tupla de barrio,arbol,cantidad_arboles
        List<ThreeGroup<String, String, Integer>> nonRatioList = new ArrayList<>();
        Iterator<Map.Entry<String,Pair<String,Integer>>> iterator = iterable.iterator();
        Map.Entry<String,Pair<String,Integer>> entry;
        while (iterator.hasNext()){
            entry = iterator.next();
            nonRatioList.add(new ThreeGroup<>(entry.getKey(), entry.getValue().getLeft(),entry.getValue().getRight()));
        }

        //ordeno la lista alfabetico por barrio y mapeo a una tupla de barrio,arbol, cantidad_arboles/cantidad_habitantes de barrio
        return nonRatioList.stream()
                .sorted(Comparator.comparing(ThreeGroup::getLeft))
                .map(threeGroup-> new ThreeGroup<>(
                        threeGroup.getLeft(),
                        threeGroup.getMiddle(),
                        threeGroup.getRight()*1.0 / neighbourhoodHabitantsMap.get(threeGroup.getLeft()).getPoblation())
                )
                .collect(Collectors.toList());
    }
}
