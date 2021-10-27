package ar.edu.itba.pod.client.utils;

import com.hazelcast.core.IList;

import java.io.IOException;
import java.nio.file.Path;

public interface CsvParser<T> {

    public IList<T> loadDataAndReturn(Path path, IList<T> iList) throws IOException;
}
