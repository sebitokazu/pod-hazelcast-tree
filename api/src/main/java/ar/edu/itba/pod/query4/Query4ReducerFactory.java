package ar.edu.itba.pod.query4;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.HashSet;
import java.util.Set;

public class Query4ReducerFactory implements ReducerFactory<String, String, Integer> {

    @Override
    public Reducer<String, Integer> newReducer(String s) {
        return new Query4Reducer();
    }

    private class Query4Reducer extends Reducer<String,Integer>{

        private volatile Set<String> treeSet;

        @Override
        public void beginReduce() {
            treeSet = new HashSet<>();
        }

        @Override
        public void reduce(String s) {
            treeSet.add(s);
        }

        @Override
        public Integer finalizeReduce() {
            return treeSet.size();
        }
    }
}
