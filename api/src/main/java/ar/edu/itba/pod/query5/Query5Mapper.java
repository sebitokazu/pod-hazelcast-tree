package ar.edu.itba.pod.query5;

import ar.edu.itba.pod.model.Pair;
import ar.edu.itba.pod.model.ThreeGroup;
import ar.edu.itba.pod.model.Tree;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query5Mapper implements Mapper<ThreeGroup<String, String, String>, Tree, ThreeGroup<String, String, String>, Integer> {
    @Override
    public void map(ThreeGroup<String, String, String> s, Tree tree, Context<ThreeGroup<String, String, String>, Integer> context) {
        ThreeGroup<String, String, String> neighbourhoodAndStreetAndTree = new ThreeGroup<>(tree.getNeighbourhoodName(), tree.getStdStreet(), tree.getCommonName());
        context.emit(neighbourhoodAndStreetAndTree, 1);
    }
}
