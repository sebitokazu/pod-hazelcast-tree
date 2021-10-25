package ar.edu.itba.pod.query5;

import ar.edu.itba.pod.model.Pair;
import ar.edu.itba.pod.model.Tree;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query5Mapper implements Mapper<String, Tree, String, Pair<String, String>> {
    @Override
    public void map(String s, Tree tree, Context<String, Pair<String, String>> context) {
        Pair streetAndTree = new Pair(tree.getStdStreet(), tree.getCommonName());
        context.emit(tree.getNeighbourhoodName(), streetAndTree);
    }
}
