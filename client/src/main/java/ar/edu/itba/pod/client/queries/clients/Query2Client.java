package ar.edu.itba.pod.client.queries.clients;

import ar.edu.itba.pod.client.queries.Query2;
import ar.edu.itba.pod.client.utils.*;
import ar.edu.itba.pod.model.Neighbourhood;
import ar.edu.itba.pod.model.Tree;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query2Client {
    private static final Logger logger = LoggerFactory.getLogger(Query2Client.class);
    private static final MyFileLogger fileLog = new MyFileLogger("text2.txt");

    public static void main(String[] args) {
        logger.info("hz-config Query2Client Starting ...");

        Options options = new Options();
        final CommandLine commandLine = Utils.parseArguments(args, options);

        if(commandLine == null)
            return;

        HazelcastInstance hazelcastInstance = Utils.clientConfiguration(
                commandLine.getOptionValue("addresses").split(";"));

        logger.info("Started Hazelcast Client...");

        final String city = commandLine.getOptionValue("city");

        IList<Tree> treeIList = hazelcastInstance.getList("trees");
        final CsvParser<Tree> treeParser = new TreeCsvParser(city);

        fileLog.log(MyFileLoggerTypes.PARSE_CSV_START);

        try {
            treeIList = treeParser.loadDataAndReturn(Paths.get(commandLine.getOptionValue("inPath") + "/arboles" + city + ".csv"), treeIList);
        } catch(IOException e) {
            logger.error("Error while parsing trees csv file.");
            return;
        }

        final CsvParser<Neighbourhood> neighbourhoodCsvParser = new NeighbourhoodCsvParser();
        Map<String,Neighbourhood> neighbourhoodMap;

        try {
            neighbourhoodMap = neighbourhoodCsvParser.toMap(Paths.get(commandLine.getOptionValue("inPath") + "/barrios" + city + ".csv"));
        } catch (IOException e) {
            logger.error("Error while parsing neighbourhoods csv file.");
            return;
        }

        fileLog.log(MyFileLoggerTypes.PARSE_CSV_END);

        Query2 query2 = new Query2(hazelcastInstance, treeIList, neighbourhoodMap,commandLine.getOptionValue("outPath") + "/query2.csv");

        fileLog.log(MyFileLoggerTypes.MAP_REDUCE_START);

        try {
            query2.run();
            logger.info("Finished running Query2");
            fileLog.log(MyFileLoggerTypes.MAP_REDUCE_END);
        } catch (ExecutionException | InterruptedException | IOException e) {
            logger.error("Error on Query2. " + e.getMessage());
        }

        logger.info("Shutting down Hazelcast client");

        treeIList.clear();
        hazelcastInstance.shutdown();
    }
}
