package ar.edu.itba.pod.query5;

public class Query5Result {
    private int tensOfSpecies;
    private String neighbour;
    private String species;
    private String firstStreet;
    private String secondStreet;

    public Query5Result(int tensOfSpecies, String neighbour, String species, String firstStreet, String secondStreet) {
        this.tensOfSpecies = tensOfSpecies;
        this.neighbour = neighbour;
        this.species = species;
        this.firstStreet = firstStreet;
        this.secondStreet = secondStreet;
    }
}
