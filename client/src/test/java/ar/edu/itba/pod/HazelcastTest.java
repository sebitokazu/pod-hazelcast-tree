package ar.edu.itba.pod;

import ar.edu.itba.pod.client.queries.Query1;
import ar.edu.itba.pod.client.queries.Query2;
import ar.edu.itba.pod.client.utils.CsvParser;
import ar.edu.itba.pod.client.utils.NeighbourhoodCsvParser;
import ar.edu.itba.pod.client.utils.TreeCsvParser;
import ar.edu.itba.pod.model.Neighbourhood;
import ar.edu.itba.pod.model.Tree;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.test.TestHazelcastFactory;
import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class HazelcastTest {
    private TestHazelcastFactory hazelcastFactory;
    private HazelcastInstance member, client;
    private final static String root_path = "./", trees_csv_path = root_path + "arbolesBUE_test.csv", neighbourhood_csv_path = root_path + "barriosBUE_test.csv";
    private IList<Tree> treeIList;
    private Map<String,Neighbourhood> neighbourhoodMap;


    @Before
    public void setUp() throws IOException {
        hazelcastFactory = new TestHazelcastFactory();

        // Group Config
        GroupConfig groupConfig = new
                GroupConfig().setName("g3").setPassword("g3-pass");

        // Config
        Config config = new Config().setGroupConfig(groupConfig);
        member = hazelcastFactory.newHazelcastInstance(config);

        // Client Config
        ClientConfig clientConfig = new ClientConfig().setGroupConfig(groupConfig);
        client = hazelcastFactory.newHazelcastClient(clientConfig);

        treeIList = member.getList("trees");

        CsvParser<Tree> treeParser = new TreeCsvParser("BUE");
        treeIList = treeParser.loadDataAndReturn(Paths.get(trees_csv_path), treeIList);

        CsvParser<Neighbourhood> neighbourhoodCsvParser = new NeighbourhoodCsvParser();
        neighbourhoodMap = neighbourhoodCsvParser.toMap(Paths.get(neighbourhood_csv_path));
    }

    @Test
    public void query1Test() throws InterruptedException, ExecutionException, IOException {
        final String outPath = root_path + "query1_test.csv";

        Query1 query1 = new Query1(member, treeIList, outPath);
        query1.run();

        List<String> lines = readAllLines(outPath);

        Assert.assertEquals(lines.size(), 9);

        Assert.assertEquals(lines.get(1), "3;9");
        Assert.assertEquals(lines.get(4), "10;3");
        Assert.assertEquals(lines.get(5), "5;3");
        Assert.assertEquals(lines.get(8), "14;1");
    }

    @Test
    public void query2Test() throws IOException, ExecutionException, InterruptedException {
        final String outPath = root_path + "query2_test.csv";

        Query2 query2 = new Query2(member, treeIList, neighbourhoodMap, outPath);
        query2.run();

        List<String> lines = readAllLines(outPath);

        Assert.assertEquals(lines.size(), 9);

        Assert.assertEquals(lines.get(1), "1;No identificado;0.04");
        Assert.assertEquals(lines.get(5), "3;Ficus benjamina;0.21");
        Assert.assertEquals(lines.get(8), "7;Fraxinus pennsylvanica;0.08");
    }


    @Test
    public void query3Test() throws InterruptedException, ExecutionException, IOException {
        final String outPath = root_path + "query3_test.csv";

        //TODO
//        Query3 query3 = new Query3(member, treeIList, neighbourhoodMap, outPath);
//        query3.run();

        List<String> lines = readAllLines(outPath);

    }

    @Test
    public void query4Test() throws InterruptedException, ExecutionException, IOException {
        final String outPath = root_path + "query4_test.csv";

        //TODO
//        Query4 query4 = new Query4(member, treeIList, neighbourhoodMap, outPath);
//        query4.run();
        List<String> lines = readAllLines(outPath);

    }

    @Test
    public void query5Test() throws InterruptedException, ExecutionException, IOException {
        final String outPath = root_path + "query5_test.csv";

        //TODO
//        Query5 query5 = new Query5(member, treeIList, neighbourhoodMap, outPath);
//        query5.run();

        List<String> lines = readAllLines(outPath);

    }

    @After
    public void tearDown() {
        treeIList.clear();
        hazelcastFactory.shutdownAll();
    }

    private List<String> readAllLines(String outPath) throws IOException {
        return Files.readAllLines(Paths.get(outPath), StandardCharsets.ISO_8859_1);
    }
}

