package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.model.Pair;
import ar.edu.itba.pod.model.Tree;
import ar.edu.itba.pod.query1.Query1Collator;
import ar.edu.itba.pod.query1.Query1CombinerFactory;
import ar.edu.itba.pod.query1.Query1Mapper;
import ar.edu.itba.pod.query1.Query1ReducerFactory;
import ar.edu.itba.pod.query4.Query4Collator;
import ar.edu.itba.pod.query4.Query4Mapper;
import ar.edu.itba.pod.query4.Query4ReducerFactory;
import ar.edu.itba.pod.query4.Query4Result;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Query4 implements Query{

    private IList<Tree> treeIList;
    private final HazelcastInstance hazelcastInstance;
    private List<Query4Result> result;
    private String resultPath;

    public Query4(IList<Tree> treeIList, HazelcastInstance hazelcastInstance, String resultPath) {
        this.treeIList = treeIList;
        this.hazelcastInstance = hazelcastInstance;
        this.resultPath = resultPath;
    }

    @Override
    public void run() throws ExecutionException, InterruptedException, IOException {
        final JobTracker jobTracker = hazelcastInstance.getJobTracker("g3_query4");
        final KeyValueSource<String, Tree> keyValueSource = KeyValueSource.fromList(treeIList);
        final Job<String, Tree> job = jobTracker.newJob(keyValueSource);
        final ICompletableFuture<List<Query4Result>> completableFuture = job
                .mapper(new Query4Mapper())
                .reducer(new Query4ReducerFactory())
                .submit(new Query4Collator());
        result = completableFuture.get();
        write(resultPath);
    }

    @Override
    public void write(String path) throws IOException {
        List<String> resLines = new ArrayList<>();
        resLines.add("GROUP;NEIGHBOURHOOD A;NEIGHBOURHOOD B");
        result.forEach(res -> resLines.add(res.toString()));
        Path outPath = Paths.get(path);
        Files.write(outPath, resLines);
    }
}
