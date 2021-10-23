package ar.edu.itba.pod.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class Tree implements DataSerializable {

    private String neighbourhoodName;
    private String stdStreet;
    private String commonName;

    public Tree(String neighbourhoodName, String stdStreet, String commonName) {
        this.neighbourhoodName = neighbourhoodName;
        this.stdStreet = stdStreet;
        this.commonName = commonName;
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {

    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {

    }

    public String getNeighbourhoodName() {
        return neighbourhoodName;
    }

    public void setNeighbourhoodName(String neighbourhoodName) {
        this.neighbourhoodName = neighbourhoodName;
    }

    public String getStdStreet() {
        return stdStreet;
    }

    public void setStdStreet(String stdStreet) {
        this.stdStreet = stdStreet;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }
}
