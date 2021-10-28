package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.model.Tree;
import ar.edu.itba.pod.query5.*;
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

public class Query5 implements Query{

    private IList<Tree> treeIList;
    private final HazelcastInstance hazelcastInstance;
    private List<Query5Result> result;
    private String resultPath;
    private String neighbourhood;
    private String commonName;

    public Query5(IList<Tree> treeIList, HazelcastInstance hazelcastInstance, String resultPath, String neighbourhood, String commonName) {
        this.treeIList = treeIList;
        this.hazelcastInstance = hazelcastInstance;
        this.resultPath = resultPath;
        this.neighbourhood = neighbourhood;
        this.commonName = commonName;
    }

    @Override
    public void run() throws ExecutionException, InterruptedException, IOException {
        final JobTracker jobTracker = hazelcastInstance.getJobTracker("g3_query5");
        final KeyValueSource<String, Tree> keyValueSource = KeyValueSource.fromList(treeIList);
        final Job<String, Tree> job = jobTracker.newJob(keyValueSource);
        final ICompletableFuture<List<Query5Result>> completableFuture = job
                .mapper(new Query5Mapper(neighbourhood,commonName))
                .combiner(new Query5CombinerFactory())
                .reducer(new Query5ReducerFactory())
                .submit(new Query5Collator());
        result = completableFuture.get();
        write(resultPath);
    }

    @Override
    public void write(String path) throws IOException {
        List<String> resLines = new ArrayList<>();
        resLines.add("GROUP;STREET A;STREET B");
        result.forEach(res -> resLines.add(res.toString()));
        Path outPath = Paths.get(path);
        Files.write(outPath, resLines);
    }
}
