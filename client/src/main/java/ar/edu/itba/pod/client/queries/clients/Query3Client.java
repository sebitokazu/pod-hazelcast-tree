package ar.edu.itba.pod.client.queries.clients;

import ar.edu.itba.pod.client.queries.Query3;
import ar.edu.itba.pod.client.queries.Query4;
import ar.edu.itba.pod.client.utils.*;
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

public class Query3Client {
    private static final Logger log = LoggerFactory.getLogger(Query3Client.class);
    private static final MyFileLogger fileLog = new MyFileLogger("text3.txt");

    public static void main(String[] args) {
        log.info("hz-config Query3Client Starting ...");

        Options options = new Options();
        // Parametros adicionales
        options.addOption("n", "n", true, "La cantidad de barrios a listar. Valor entero mayor a cero.");

        final CommandLine commandLine = Utils.parseArguments(args, options);

        if(commandLine == null)
            return;

        int limit = 0;
        try {
            limit = Integer.parseInt(commandLine.getOptionValue("n"));
        } catch(NumberFormatException e) {
            log.error("Invalid parameter n.");
            return;
        }

        if(limit <= 0) {
            log.error("n must be higher than 0.");
            return;
        }

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

        Query3 query3 = new Query3(treeIList, hazelcastInstance , commandLine.getOptionValue("outPath") + "/query3.csv", limit);

        fileLog.log(MyFileLoggerTypes.MAP_REDUCE_START);
        try {
            query3.run();
            log.info("Finished running Query3");
            fileLog.log(MyFileLoggerTypes.MAP_REDUCE_END);
        } catch (IOException | ExecutionException | InterruptedException e) {
            log.error("Error on Query3. " + e.getMessage());
            return;
        }

        log.info("Shutting down Hazelcast client");
        treeIList.clear();
        hazelcastInstance.shutdown();
    }
}
