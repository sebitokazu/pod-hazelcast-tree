package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.model.Neighbourhood;
import ar.edu.itba.pod.model.Tree;
import ar.edu.itba.pod.query1.Query1CombinerFactory;
import ar.edu.itba.pod.query1.Query1Mapper;
import ar.edu.itba.pod.query1.Query1ReducerFactory;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import com.hazelcast.mapreduce.Mapper;

;import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query1 implements Query {

    private IList<Tree> treeIList;
    private final HazelcastInstance hazelcastInstance;

    public Query1(HazelcastInstance instance, IList<Tree> treeIList) {
        this.treeIList = treeIList;
        this.hazelcastInstance = instance;
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        final JobTracker jobTracker = hazelcastInstance.getJobTracker("g3_query1");
        final KeyValueSource<String, Tree> keyValueSource = KeyValueSource.fromList(treeIList);
        final Job<String, Tree> job = jobTracker.newJob(keyValueSource);
        final ICompletableFuture<Map<String, Integer>> completableFuture = job
                .mapper(new Query1Mapper())
                .combiner(new Query1CombinerFactory())
                .reducer(new Query1ReducerFactory())
                .submit();
        final Map<String, Integer> result = completableFuture.get();
        System.out.println("a");
    }

    @Override
    public void write() {

    }

    @Override
    public String read() {
        return null;
    }
}
