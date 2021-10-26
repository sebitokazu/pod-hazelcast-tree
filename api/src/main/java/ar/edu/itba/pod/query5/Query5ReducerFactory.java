package ar.edu.itba.pod.query5;

import ar.edu.itba.pod.model.Pair;
import ar.edu.itba.pod.model.ThreeGroup;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Query5ReducerFactory implements ReducerFactory<ThreeGroup<String, String, String>, Integer, Integer> {
    @Override
    public Reducer<Integer, Integer> newReducer(ThreeGroup<String, String, String> stringStringStringThreeGroup) {
        return new Query5Reducer();
    }

    private class Query5Reducer extends Reducer<Integer, Integer>{

        private int sum = 0;

        @Override
        public void reduce(Integer integer) {
            sum += integer;
        }

        @Override
        public Integer finalizeReduce() {
            return sum;
        }
    }
}
