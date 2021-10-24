package ar.edu.itba.pod.query3;

import ar.edu.itba.pod.model.Tree;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query3Mapper implements Mapper<String, Tree, String, String> {
    @Override
    public void map(String s, Tree tree, Context<String, String> context) {
        context.emit(tree.getNeighbourhoodName(),tree.getCommonName());
    }
}
