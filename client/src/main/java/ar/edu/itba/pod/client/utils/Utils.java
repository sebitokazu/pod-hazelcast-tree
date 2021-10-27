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
    public static CommandLine parseArguments(String[] args, Options options) {
        options.addOption("city", "city", true,"Indica con qué dataset de ciudad se desea trabajar. Los únicos valores posibles son BUE y VAN.");
        options.addOption("addresses", "addresses", true,"Refiere a las direcciones IP de los nodos con sus puertos (una o más, separadas por punto y coma).");
        options.addOption("inPath", "inPath", true,"Indica el path donde están los archivos de entrada de barrios y de arboles.");
        options.addOption("outPath","outPath", true,"Indica el path donde estarán ambos archivos de salida query1.csv y time1.txt.");


        final CommandLineParser commandLineParser = new DefaultParser();

        try {
            return commandLineParser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("Invalid argument: " + e.getMessage());
            return null;
        }
    }
}
