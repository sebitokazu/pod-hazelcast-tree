package ar.edu.itba.pod.query5;

import ar.edu.itba.pod.model.ThreeGroup;
import ar.edu.itba.pod.query1.Query1CombinerFactory;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class Query5CombinerFactory implements CombinerFactory<String, Integer, Integer> {
    @Override
    public Combiner<Integer, Integer> newCombiner(String streetStd) {
        return new Query5CombinerFactory.Query5Combiner();
    }

    public class Query5Combiner extends Combiner<Integer, Integer> {
        private int sum = 0;

        @Override
        public void combine(Integer integer) {
            sum += integer;
        }

        @Override
        public Integer finalizeChunk() {
            return sum;
        }

        public void reset(){
            sum = 0;
        }
    }
}
