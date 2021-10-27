package ar.edu.itba.pod.client.utils;

import com.hazelcast.core.IList;
import ar.edu.itba.pod.model.City;
import ar.edu.itba.pod.model.Tree;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreeCsvParser implements CsvParser<Tree>{

    private String city;

    public TreeCsvParser(String city) {
        this.city = city;
    }

    Tree parseTree(String line){
        String[] column = line.split(";");
        if(this.city.equals("BUE"))
            return new Tree(column[2], column[4], column[7]);
        else return new Tree(column[12], column[2], column[6]);
    }

    @Override
    public IList<Tree> loadDataAndReturn(Path path, IList<Tree> treeIList) throws IOException {
        List<String> stream = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
        List<Tree> treeList = stream.stream().skip(1).map(this::parseTree).collect(Collectors.toList());
        treeIList.addAll(treeList);
        return treeIList;
    }

}
