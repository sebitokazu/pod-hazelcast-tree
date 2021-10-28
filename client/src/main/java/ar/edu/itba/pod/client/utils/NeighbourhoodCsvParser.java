package ar.edu.itba.pod.client.utils;

import ar.edu.itba.pod.model.City;
import ar.edu.itba.pod.model.Neighbourhood;
import ar.edu.itba.pod.model.Tree;
import com.hazelcast.core.IList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NeighbourhoodCsvParser implements CsvParser<Neighbourhood>{

    Neighbourhood parseNeighbourhood(String line){
        String[] column = line.split(";");
        return new Neighbourhood(column[0], Integer.parseInt(column[1]));
    }

    @Override
    public IList<Neighbourhood> loadDataAndReturn(Path path, IList<Neighbourhood> neighbourhoodIList) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
        List<Neighbourhood> neighbourhoodList = lines.stream().skip(1).map(this::parseNeighbourhood).collect(Collectors.toList());
        neighbourhoodIList.addAll(neighbourhoodList);
        return neighbourhoodIList;
    }

    @Override
    public Map<String,Neighbourhood> toMap(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path,StandardCharsets.ISO_8859_1);
        return lines.stream()
                .skip(1)
                .map(this::parseNeighbourhood)
                .collect(Collectors.toMap(Neighbourhood::getName, Function.identity()));
    }
}
