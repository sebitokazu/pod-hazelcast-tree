package ar.edu.itba.pod.query3;

import ar.edu.itba.pod.model.Pair;
import com.hazelcast.mapreduce.Collator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Query3Collator implements Collator<Map.Entry<String, Integer>, List<Pair<String, Integer>>> {
    private final int N;

    public Query3Collator(int N){
        this.N = N;
    }

    @Override
    public List<Pair<String, Integer>> collate(Iterable<Map.Entry<String, Integer>> iterable) {
        List<Pair<String,Integer>> neighbourhoodAndDifferentSpecies = new ArrayList<>();
        iterable.forEach(entry -> neighbourhoodAndDifferentSpecies.add(new Pair<>(entry.getKey(),entry.getValue())));
        return neighbourhoodAndDifferentSpecies
                .stream()
                .sorted((p1,p2) -> {
                    int aux = p2.getRight().compareTo(p1.getRight());
                    if(aux != 0) return aux;
                    else return p1.getLeft().compareTo(p2.getLeft());
                })
                .limit(N)
                .collect(Collectors.toList());
    }
}
