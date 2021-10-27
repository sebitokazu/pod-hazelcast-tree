package ar.edu.itba.pod.model;

import java.util.Objects;

public class Pair<K,V> {

    private K left;
    private V right;

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
}
