package ar.edu.itba.pod.query5;

import ar.edu.itba.pod.model.Pair;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Query5ReducerFactory implements ReducerFactory<String, Pair<String, String>, Map<Pair<String, String>, Integer>> {
    @Override
    public Reducer<Pair<String, String>, Map<Pair<String, String>, Integer>> newReducer(String s) {
        return new Query5Reducer();
    }

    private class Query5Reducer extends Reducer<Pair<String, String>, Map<Pair<String, String>, Integer>>{

        //Mapa para contar la cantidad de arboles en cada calle
        private volatile Map<Pair<String, String>, Integer> streetAndTreeAndCountMap;

        @Override
        public void beginReduce() {
            streetAndTreeAndCountMap = new HashMap<>();
        }

        @Override
        public void reduce(Pair<String, String> pair) {
            if(!streetAndTreeAndCountMap.containsKey(pair))
                streetAndTreeAndCountMap.put(pair,1); //Agrego la key al map
            else streetAndTreeAndCountMap.put(pair, streetAndTreeAndCountMap.get(pair) + 1); //Sumo un tree
        }

        /**
         * Para cada barrio se devuelve un mapa con la calle y tipo de arbol y como value la cantidad de ese tipo
         */
        @Override
        public Map<Pair<String, String>, Integer> finalizeReduce() {
            return streetAndTreeAndCountMap;
        }
    }
}
