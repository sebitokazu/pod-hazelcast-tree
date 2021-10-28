package ar.edu.itba.pod;

import ar.edu.itba.pod.client.queries.*;
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
    private final static String trees_csv_path = "arbolesBUE_test.csv", neighbourhood_csv_path =  "barriosBUE_test.csv";
    private IList<Tree> treeIList;
    private Map<String,Neighbourhood> neighbourhoodMap;
    private final int CSV_LINES_QTY = 9;


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
        treeIList = treeParser.loadDataAndReturn(Paths.get("src","test","resources", trees_csv_path), treeIList);

        // TODO: Estamos parseando el csv de barrios en el @Before y solo lo usa la query 2
        CsvParser<Neighbourhood> neighbourhoodCsvParser = new NeighbourhoodCsvParser();
        neighbourhoodMap = neighbourhoodCsvParser.toMap(Paths.get("src","test","resources", neighbourhood_csv_path));
    }

    @Test
    public void query1Test() throws InterruptedException, ExecutionException, IOException {
        final String outPath = "query1_test.csv";

        Query1 query1 = new Query1(member, treeIList, outPath);
        query1.run();

        List<String> lines = readAllLines(outPath);

        Assert.assertEquals(lines.size(), CSV_LINES_QTY);

        Assert.assertEquals(lines.get(1), "3;9");
        Assert.assertEquals(lines.get(4), "10;3");
        Assert.assertEquals(lines.get(5), "5;3");
        Assert.assertEquals(lines.get(8), "14;1");
    }

    @Test
    public void query2Test() throws IOException, ExecutionException, InterruptedException {
        final String outPath = "query2_test.csv";

        Query2 query2 = new Query2(member, treeIList, neighbourhoodMap, outPath);
        query2.run();

        List<String> lines = readAllLines(outPath);

        Assert.assertEquals(lines.size(), CSV_LINES_QTY);

        Assert.assertEquals(lines.get(1), "1;No identificado;0.04");
        Assert.assertEquals(lines.get(5), "3;Ficus benjamina;0.21");
        Assert.assertEquals(lines.get(8), "7;Fraxinus pennsylvanica;0.08");
    }


    @Test
    public void query3Test() throws InterruptedException, ExecutionException, IOException {
        final String outPath = "query3_test.csv";
        final int LIMIT = 6;

        Query3 query3 = new Query3(treeIList, member, outPath, LIMIT);
        query3.run();

        List<String> lines = readAllLines(outPath);

        Assert.assertEquals(lines.size(), LIMIT > CSV_LINES_QTY ? CSV_LINES_QTY : LIMIT + 1); //no aceptar la sugerencia del ide

        Assert.assertEquals(lines.get(1), "15;4");
        Assert.assertEquals(lines.get(2), "3;4");
        Assert.assertEquals(lines.get(3), "5;3");
        Assert.assertEquals(lines.get(4), "1;2");
    }


    @Test
    public void query4Test() throws InterruptedException, ExecutionException, IOException {
        final String outPath = "query4_test.csv";
        CsvParser<Tree> treeParser = new TreeCsvParser("BUE");
        treeIList = treeParser.loadDataAndReturn(Paths.get("src","test","resources", "arbolesBUE.csv"), treeIList);

        Query4 query4 = new Query4(treeIList, member, outPath);
        query4.run();

        List<String> lines = readAllLines(outPath);

        Assert.assertEquals(lines.get(2), "200;10;12");
        boolean a = lines.contains("200;10;13") && lines.contains("200;10;15") && lines.contains("200;13;15") && !lines.contains("200;15;13");
        Assert.assertTrue(a);
        Assert.assertFalse(lines.contains("200;10;10"));
        Assert.assertEquals(lines.get(lines.size()-1), "100;5;6");
    }

    @Test
    public void query4DontPrintGroup0Test() throws InterruptedException, ExecutionException, IOException {
        final String outPath = "query4_test.csv";

        Query4 query4 = new Query4(treeIList, member, outPath);
        query4.run();

        List<String> lines = readAllLines(outPath);

        Assert.assertEquals(lines.size(), 1); //solo el header
    }

    @Test
    public void query5Test() throws InterruptedException, ExecutionException, IOException {
        final String outPath = "query5_test.csv";
        final String NEIGHBOURHOOD = "3";
        final String COMMON_NAME = "Ficus benjamina";
        CsvParser<Tree> treeParser = new TreeCsvParser("BUE");
        treeIList = treeParser.loadDataAndReturn(Paths.get("src","test","resources", "arbolesBUE.csv"), treeIList);

        Query5 query5 = new Query5(treeIList, member, outPath, NEIGHBOURHOOD, COMMON_NAME);
        query5.run();

        List<String> lines = readAllLines(outPath);

        Assert.assertEquals(lines.get(1), "30;Catamarca;Ecuador");
        Assert.assertFalse(lines.contains("30;Ecuador;Catamarca"));
        Assert.assertFalse(lines.contains("30;Catamarca;Catamarca"));
        Assert.assertEquals(lines.get(lines.size()-1), "10;Venezuela;Viamonte");
    }

    @Test
    public void query5DontPrintGroup0Test() throws InterruptedException, ExecutionException, IOException {
        final String outPath = "query5_test.csv";
        final String NEIGHBOURHOOD = "3";
        final String COMMON_NAME = "Ficus benjamina";

        Query5 query5 = new Query5(treeIList, member, outPath, NEIGHBOURHOOD, COMMON_NAME);
        query5.run();

        List<String> lines = readAllLines(outPath);

        Assert.assertEquals(lines.size(), 1); //solo el header
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

