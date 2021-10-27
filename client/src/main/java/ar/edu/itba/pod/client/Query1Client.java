package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.Utils;
import com.hazelcast.core.HazelcastInstance;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Query1Client {
    private static final Logger logger = LoggerFactory.getLogger(Query1Client.class);

    public static void main(String[] args) throws InterruptedException {
        logger.info("hz-config Query1Client Starting ...");

        Options options = new Options();
        final CommandLine commandLine = Utils.parseArguments(args, options);

        if(commandLine == null)
            return;

        HazelcastInstance hazelcastInstance = Utils.clientConfiguration(
                commandLine.getOptionValue("addresses").split(";"));



        hazelcastInstance.shutdown();
    }
}
