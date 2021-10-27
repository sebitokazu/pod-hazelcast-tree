package ar.edu.itba.pod.client.queries.clients;

import ar.edu.itba.pod.client.utils.Utils;
import com.hazelcast.core.HazelcastInstance;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Query3Client {
    private static final Logger logger = LoggerFactory.getLogger(Query3Client.class);

    public static void main(String[] args) {
        logger.info("hz-config Query3Client Starting ...");

        Options options = new Options();
        // Parametros adicionales
        options.addOption("n", "La cantidad de barrios a listar. Valor entero mayor a cero.");

        final CommandLine commandLine = Utils.parseArguments(args, options);

        if(commandLine == null)
            return;

        // TODO: Chequear que n sea natural

        HazelcastInstance hazelcastInstance = Utils.clientConfiguration(
                commandLine.getOptionValue("addresses").split(";"));

        //TODO: Query

        hazelcastInstance.shutdown();
    }
}
