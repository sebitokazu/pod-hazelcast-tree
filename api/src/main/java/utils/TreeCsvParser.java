package utils;

import com.hazelcast.core.IList;
import model.City;
import model.Tree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreeCsvParser implements CsvParser<Tree>{

    private City city;

    Tree parseTree(String line){
        String[] column = line.split(";");
        if(this.city.equals(City.BUENOS_AIRES))
            return new Tree(column[4], column[6], column[11]);
        else return new Tree(column[12], column[2], column[6]);
    }

    @Override
    public IList<Tree> loadDataAndReturn(Path path, IList<Tree> treeIList) throws IOException {
        Stream<String> stream = Files.lines(path);
        List<Tree> treeList = stream.skip(1).map(this::parseTree).collect(Collectors.toList());
        treeIList.addAll(treeList);
        return treeIList;
    }

}
