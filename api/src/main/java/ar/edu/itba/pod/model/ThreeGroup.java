package ar.edu.itba.pod.model;

import java.util.Objects;

public class ThreeGroup<K, V, S> {

    private K left;
    private V middle;
    private S right;

    public ThreeGroup(K left, V middle, S right){
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public K getLeft() {
        return left;
    }

    public V getMiddle() {
        return middle;
    }

    public S getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThreeGroup<?, ?, ?> threeGroup = (ThreeGroup<?, ?, ?>) o;
        return getLeft().equals(threeGroup.getLeft()) && getMiddle().equals(threeGroup.getMiddle()) && getRight().equals(threeGroup.getRight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLeft(), getMiddle(),getRight());
    }

    public boolean equalsLeft(K k){
        return this.getLeft().equals(k);
    }

    public boolean equalsRight(S s){
        return this.getRight().equals(s);
    }

    public boolean equalsMiddle(V v){
        return this.getMiddle().equals(v);
    }
}
