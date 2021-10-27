package ar.edu.itba.pod.query2;

import ar.edu.itba.pod.model.Pair;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class Query2ReducerFactory1 implements ReducerFactory<String,String, Pair<String,Integer>> {
    @Override
    public Reducer<String, Pair<String, Integer>> newReducer(String s) {
        return new Query2Reducer1();
    }

    private class Query2Reducer1 extends Reducer<String,Pair<String,Integer>>{

        private volatile Map<String, Integer> treeAndCountMap;

        @Override
        public void beginReduce() {
            treeAndCountMap = new HashMap<>();
        }

        @Override
        public void reduce(String s) {
            if(!treeAndCountMap.containsKey(s))
                treeAndCountMap.put(s,1);
            else treeAndCountMap.put(s,treeAndCountMap.get(s) + 1);
        }

        @Override
        public Pair<String, Integer> finalizeReduce() {
            String maxTreeName = "";
            int maxTreeCount = 0;

            for (Map.Entry<String,Integer> entry: treeAndCountMap.entrySet()){
                if(entry.getValue() > maxTreeCount){
                    maxTreeName = entry.getKey();
                    maxTreeCount = entry.getValue();
                }
            }

            return new Pair<>(maxTreeName,maxTreeCount);
        }
    }
}
