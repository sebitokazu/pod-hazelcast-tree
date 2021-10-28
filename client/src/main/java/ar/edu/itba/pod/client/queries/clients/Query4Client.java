package ar.edu.itba.pod.client.queries.clients;

import ar.edu.itba.pod.client.queries.Query1;
import ar.edu.itba.pod.client.queries.Query4;
import ar.edu.itba.pod.client.utils.*;
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

public class Query4Client {
    private static final Logger log = LoggerFactory.getLogger(Query4Client.class);

    public static void main(String[] args) {
        log.info("hz-config Query4Client Starting ...");

        Options options = new Options();
        final CommandLine commandLine = Utils.parseArguments(args, options);

        if(commandLine == null)
            return;

        MyFileLogger fileLog = new MyFileLogger(commandLine.getOptionValue("outPath") + "/text4.txt");

        HazelcastInstance hazelcastInstance = Utils.clientConfiguration(
                commandLine.getOptionValue("addresses").split(";"));

        log.info("Started Hazelcast Client...");

        final String city = commandLine.getOptionValue("city");

        IList<Tree> treeIList = hazelcastInstance.getList("trees");
        final CsvParser<Tree> treeParser = new TreeCsvParser(city);

        fileLog.log(MyFileLoggerTypes.PARSE_CSV_START);

        try {
            treeIList = treeParser.loadDataAndReturn(Paths.get(commandLine.getOptionValue("inPath") + "/arboles" + city + ".csv"), treeIList);
        } catch(IOException e) {
            log.error("Error while parsing trees csv file.");
            return;
        }

        fileLog.log(MyFileLoggerTypes.PARSE_CSV_END);

        Query4 query4 = new Query4(treeIList, hazelcastInstance, commandLine.getOptionValue("outPath") + "/query4.csv");

        fileLog.log(MyFileLoggerTypes.MAP_REDUCE_START);

        try {
            query4.run();
            log.info("Finished running Query4");
            fileLog.log(MyFileLoggerTypes.MAP_REDUCE_END);
        } catch (IOException | ExecutionException | InterruptedException e) {
            log.error("Error on Query4. " + e.getMessage());
            return;
        }

        log.info("Shutting down Hazelcast client");
        treeIList.clear();
        hazelcastInstance.shutdown();
    }
}
