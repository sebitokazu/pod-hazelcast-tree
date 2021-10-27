package ar.edu.itba.pod.client.queries.clients;

import ar.edu.itba.pod.client.utils.Utils;
import com.hazelcast.core.HazelcastInstance;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Query5Client {
    private static final Logger logger = LoggerFactory.getLogger(Query5Client.class);

    public static void main(String[] args) {
        logger.info("hz-config Query5Client Starting ...");

        Options options = new Options();
        // Parametros adicionales
        options.addOption("neighbourhood", "Nombre del barrio.");
        options.addOption("commonName", "Nombre de la especie.");

        final CommandLine commandLine = Utils.parseArguments(args, options);

        if(commandLine == null)
            return;

        //TODO: neighbourhood y commonName --> chequear que ambos sean no vacios

        HazelcastInstance hazelcastInstance = Utils.clientConfiguration(
                commandLine.getOptionValue("addresses").split(";"));

        //TODO: Query

        hazelcastInstance.shutdown();
    }
}
