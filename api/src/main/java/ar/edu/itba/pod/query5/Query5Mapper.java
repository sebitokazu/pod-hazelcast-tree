package ar.edu.itba.pod.query5;

import ar.edu.itba.pod.model.Pair;
import ar.edu.itba.pod.model.ThreeGroup;
import ar.edu.itba.pod.model.Tree;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query5Mapper implements Mapper<String, Tree, String, Integer> {

    private final String commonName;
    private final String neighbourhoodName;

    public Query5Mapper(String neighbourhoodName,String commonName){
        this.neighbourhoodName = neighbourhoodName;
        this.commonName = commonName;
    }

    public void map(String s, Tree tree, Context<String, Integer> context) {
        if(tree.getCommonName().equals(commonName) && tree.getNeighbourhoodName().equals(neighbourhoodName)) {
            context.emit(tree.getStdStreet(), 1);
        }
    }
}
