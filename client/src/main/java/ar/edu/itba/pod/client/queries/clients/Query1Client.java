package ar.edu.itba.pod.client.queries.clients;

import ar.edu.itba.pod.client.queries.Query1;
import ar.edu.itba.pod.client.utils.CsvParser;
import ar.edu.itba.pod.client.utils.TreeCsvParser;
import ar.edu.itba.pod.client.utils.Utils;
import ar.edu.itba.pod.model.City;
import ar.edu.itba.pod.model.Tree;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

public class Query1Client {
    private static final Logger log = LoggerFactory.getLogger(Query1Client.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        log.info("hz-config Query1Client Starting ...");

        Options options = new Options();
        final CommandLine commandLine = Utils.parseArguments(args, options);

        if(commandLine == null)
            return;

        HazelcastInstance hazelcastInstance = Utils.clientConfiguration(
                commandLine.getOptionValue("addresses").split(";"));

        log.info("Started Hazelcast Client...");

        final String city = commandLine.getOptionValue("city");

        IList<Tree> treeIList = hazelcastInstance.getList("trees");
        final CsvParser<Tree> treeParser = new TreeCsvParser(city);
        try {
            treeIList = treeParser.loadDataAndReturn(Paths.get(commandLine.getOptionValue("inPath") + "/arboles" + city + ".csv"), treeIList);
        } catch(IOException e) {
            log.error("Error while parsing trees csv file.");
        }

        Query1 query1 = new Query1(hazelcastInstance, treeIList, commandLine.getOptionValue("outPath") + "/query1.csv");
        try {
            query1.run();
            log.info("Finished running Query1");
        } catch (IOException e) {
            log.error("Error on Query1. " + e.getMessage());
        }

        log.info("Shutting down Hazelcast client");
        treeIList.clear();
        HazelcastClient.shutdownAll();
    }
}
