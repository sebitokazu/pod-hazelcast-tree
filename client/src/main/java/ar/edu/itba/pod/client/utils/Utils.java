package ar.edu.itba.pod.client.utils;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;

import org.apache.commons.cli.*;

public class Utils {
    public static HazelcastInstance clientConfiguration(String[] addresses) { //TODO: Hay que parametrizar algo mas?
        // Client Config
        ClientConfig clientConfig = new ClientConfig();

        // Group Config
        GroupConfig groupConfig = new
                GroupConfig().setName("g3").setPassword("g3-pass");
        clientConfig.setGroupConfig(groupConfig);

        // Client Network Config
        ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();
        clientNetworkConfig.addAddress(addresses);
        clientConfig.setNetworkConfig(clientNetworkConfig);

        return HazelcastClient.newHazelcastClient(clientConfig);
    }

    /* Recibe las options por parametro porque algunas clases tienen mas argumentos. */
    public static CommandLine parseArguments(String[] args, Options options, String errorMsg) {
        //TODO: add all options shared by all clients

        final CommandLineParser commandLineParser = new DefaultParser();

        try {
            return commandLineParser.parse(options, args);
        } catch (ParseException e) {
            errorMsg = "Invalid argument: " + e.getMessage(); //TODO: Chequear que se este seteando
            return null;
        }
    }
}
