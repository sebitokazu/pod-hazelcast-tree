package ar.edu.itba.pod.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.util.Objects;

public class Neighbourhood implements DataSerializable {

    private String name;
    private int poblation;

    public Neighbourhood() {
    }

    public Neighbourhood(String name, int poblation) {
        this.name = name;
        this.poblation = poblation;
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeUTF(name);
        objectDataOutput.writeInt(poblation);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        this.name = objectDataInput.readUTF();
        this.poblation = objectDataInput.readInt();
    }

    public String getName() {
        return name;
    }

    public int getPoblation() {
        return poblation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neighbourhood that = (Neighbourhood) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
