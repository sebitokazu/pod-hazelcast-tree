package ar.edu.itba.pod.client;

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

        String errorMsg = "";
        Options options = new Options(); //TODO: Parametro adicional --> neighbourhood y commonName (chequear que ambos sean no vacios)
        final CommandLine commandLine = Utils.parseArguments(args, options, errorMsg);

        if(commandLine == null) {
            logger.error(errorMsg);
            return;
        }

        HazelcastInstance hazelcastInstance = Utils.clientConfiguration(
                commandLine.getOptionValue("addresses").split(";"));
    }
}
