package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.model.Pair;
import ar.edu.itba.pod.model.Tree;
import ar.edu.itba.pod.query3.Query3Collator;
import ar.edu.itba.pod.query3.Query3Mapper;
import ar.edu.itba.pod.query3.Query3ReducerFactory;
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

public class Query3 implements Query{

    private IList<Tree> treeIList;
    private final HazelcastInstance hazelcastInstance;
    private List<Pair<String, Integer>> result;
    private String resultPath;
    private int limit;

    public Query3(IList<Tree> treeIList, HazelcastInstance hazelcastInstance, String resultPath, int limit) {
        this.treeIList = treeIList;
        this.hazelcastInstance = hazelcastInstance;
        this.resultPath = resultPath;
        this.limit = limit;
    }

    @Override
    public void run() throws ExecutionException, InterruptedException, IOException {
        final JobTracker jobTracker = hazelcastInstance.getJobTracker("g3_query4");
        final KeyValueSource<String, Tree> keyValueSource = KeyValueSource.fromList(treeIList);
        final Job<String, Tree> job = jobTracker.newJob(keyValueSource);
        final ICompletableFuture<List<Pair<String, Integer>>> completableFuture = job
                .mapper(new Query3Mapper())
                .reducer(new Query3ReducerFactory())
                .submit(new Query3Collator(limit));
        result = completableFuture.get();
        write(resultPath);
    }

    @Override
    public void write(String path) throws IOException {
        List<String> resLines = new ArrayList<>();
        resLines.add("NEIGHBOURHOOD;COMMON_NAME_COUNT");
        result.forEach(res -> resLines.add(res.toString()));
        Path outPath = Paths.get(path);
        Files.write(outPath, resLines);
    }
}
