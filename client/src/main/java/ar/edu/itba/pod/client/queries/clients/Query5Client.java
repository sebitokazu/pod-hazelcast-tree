package ar.edu.itba.pod.client.queries.clients;

import ar.edu.itba.pod.client.queries.Query5;
import ar.edu.itba.pod.client.utils.CsvParser;
import ar.edu.itba.pod.client.utils.TreeCsvParser;
import ar.edu.itba.pod.client.utils.Utils;
import ar.edu.itba.pod.model.Tree;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

public class Query5Client {
    private static final Logger log = LoggerFactory.getLogger(Query5Client.class);

    public static void main(String[] args) {
        log.info("hz-config Query5Client Starting ...");

        Options options = new Options();
        // Parametros adicionales
        options.addOption("neighbourhood","neighbourhood" ,true,"Nombre del barrio.");
        options.addOption("commonName", "commonName", true,"Nombre de la especie.");

        final CommandLine commandLine = Utils.parseArguments(args, options);

        if(commandLine == null)
            return;
        String neighbourhood = commandLine.getOptionValue("neighbourhood");
        String commonName = commandLine.getOptionValue("commonName");

        //TODO: neighbourhood y commonName --> chequear que ambos sean no vacios

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

        Query5 query5 = new Query5(treeIList, hazelcastInstance, commandLine.getOptionValue("outPath") + "/query5.csv", neighbourhood, commonName);
        try {
            query5.run();
            log.info("Finished running query5");
        } catch (IOException | ExecutionException | InterruptedException e) {
            log.error("Error on query5. " + e.getMessage());
        }

        log.info("Shutting down Hazelcast client");
        treeIList.clear();

        hazelcastInstance.shutdown();
    }
}
