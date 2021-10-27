package ar.edu.itba.pod.client.queries;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface Query {

    void run() throws ExecutionException, InterruptedException, IOException;
    void write(String path) throws IOException;
}
