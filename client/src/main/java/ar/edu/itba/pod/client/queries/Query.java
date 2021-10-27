package ar.edu.itba.pod.client.queries;

import java.util.concurrent.ExecutionException;

public interface Query {

    void run() throws ExecutionException, InterruptedException;
    void write();
    String read();
}
