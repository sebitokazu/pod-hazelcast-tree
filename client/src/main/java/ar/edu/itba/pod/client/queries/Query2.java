package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.model.Neighbourhood;
import ar.edu.itba.pod.model.Pair;
import ar.edu.itba.pod.model.ThreeGroup;
import ar.edu.itba.pod.model.Tree;
import ar.edu.itba.pod.query2.Query2Collator;
import ar.edu.itba.pod.query2.Query2Mapper;
import ar.edu.itba.pod.query2.Query2ReducerFactory;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query2 implements Query{

    private IList<Tree> treeIList;
    private Map<String, Neighbourhood> neighbourhoodIntegerMap;
    private final HazelcastInstance hazelcastInstance;
    private String resultPath;
    private List<ThreeGroup<String,String,Double>> result;

    public Query2(HazelcastInstance hazelcastInstance, IList<Tree> treeIList, Map<String, Neighbourhood> nneighbourhoodIntegerMap, String resultPath) {
        this.treeIList = treeIList;
        this.neighbourhoodIntegerMap = nneighbourhoodIntegerMap;
        this.hazelcastInstance = hazelcastInstance;
        this.resultPath = resultPath;
    }

    @Override
    public void run() throws ExecutionException, InterruptedException, IOException {
        final JobTracker jobTracker = hazelcastInstance.getJobTracker("g3_query2");
        final KeyValueSource<String, Tree> keyValueSource = KeyValueSource.fromList(treeIList);
        final Job<String, Tree> job = jobTracker.newJob(keyValueSource);
        final ICompletableFuture<List<ThreeGroup<String,String,Double>>> completableFuture = job
                .mapper(new Query2Mapper())
                .reducer(new Query2ReducerFactory())
                .submit(new Query2Collator(neighbourhoodIntegerMap));
        result = completableFuture.get();
        write(resultPath);
    }

    @Override
    public void write(String path) throws IOException {
        List<String> resLines = new ArrayList<>();
        resLines.add("NEIGHBOURHOOD;COMMON_NAME;TREES_PER_PEOPLE");
        result.forEach(res -> resLines.add(res.getLeft()+";"+res.getMiddle() + ";" + String.format("%.2f",res.getRight())));
        Path outPath = Paths.get(path);
        Files.write(outPath, resLines);
    }
}
