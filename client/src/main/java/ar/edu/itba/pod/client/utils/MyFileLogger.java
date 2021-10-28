package ar.edu.itba.pod.client.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class MyFileLogger {
    private Path path;

    public MyFileLogger(String fileName) {
        this.path = Paths.get(fileName);
    }

    public void log(MyFileLoggerTypes type) {
        byte[] strToByte = (LocalDateTime.now().toString() + " - " + type.toString() + '\n').getBytes();

        try {
            Files.write(path, strToByte, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
