package ar.edu.itba.pod.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.util.Objects;

public class Pair<K,V> implements DataSerializable {

    private K left;
    private V right;

    public Pair(){}

    public Pair(K left, V right){
        this.left = left;
        this.right = right;
    }

    public K getLeft() {
        return left;
    }

    public V getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return getLeft().equals(pair.getLeft()) && getRight().equals(pair.getRight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLeft(), getRight());
    }

    @Override
    public String toString() {
        return left+";"+right;
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeObject(left);
        objectDataOutput.writeObject(right);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        this.left = objectDataInput.readObject();
        this.right = objectDataInput.readObject();
    }
}
