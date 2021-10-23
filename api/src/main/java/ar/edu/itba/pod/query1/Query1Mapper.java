package ar.edu.itba.pod.query1;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import ar.edu.itba.pod.model.Tree;

public class Query1Mapper implements Mapper<String, Tree, String, Integer> {

    @Override
    public void map(String s, Tree tree, Context<String, Integer> context) {
        context.emit(tree.getNeighbourhoodName(), 1);
    }
}
