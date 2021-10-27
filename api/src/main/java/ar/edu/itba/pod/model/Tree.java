package ar.edu.itba.pod.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.util.Objects;

public class Tree implements DataSerializable {

    private String neighbourhoodName;
    private String stdStreet;
    private String commonName;

    public Tree() {
    }

    public Tree(String neighbourhoodName, String stdStreet, String commonName) {
        this.neighbourhoodName = neighbourhoodName;
        this.stdStreet = stdStreet;
        this.commonName = commonName;
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeUTF(neighbourhoodName);
        objectDataOutput.writeUTF(stdStreet);
        objectDataOutput.writeUTF(commonName);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        this.neighbourhoodName = objectDataInput.readUTF();
        this.stdStreet = objectDataInput.readUTF();
        this.commonName = objectDataInput.readUTF();
    }

    @Override
    public String toString() {
        return "Tree{" +
                "neighbourhoodName='" + neighbourhoodName + '\'' +
                ", stdStreet='" + stdStreet + '\'' +
                ", commonName='" + commonName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree tree = (Tree) o;
        return neighbourhoodName.equals(tree.neighbourhoodName) && stdStreet.equals(tree.stdStreet) && commonName.equals(tree.commonName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(neighbourhoodName, stdStreet, commonName);
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
