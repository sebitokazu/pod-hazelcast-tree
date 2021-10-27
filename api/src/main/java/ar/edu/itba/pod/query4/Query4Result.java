package ar.edu.itba.pod.query4;

public class Query4Result {
    private int hundredsOfSpecies;
    private String firstNeighbour;
    private String secondNeighbour;

    public Query4Result(int hundredsOfSpecies, String firstNeighbour, String secondNeighbour) {
        this.hundredsOfSpecies = hundredsOfSpecies;
        this.firstNeighbour = firstNeighbour;
        this.secondNeighbour = secondNeighbour;
    }

    public int getHundredsOfSpecies() {
        return hundredsOfSpecies;
    }

    public String getFirstNeighbour() {
        return firstNeighbour;
    }

    public String getSecondNeighbour() {
        return secondNeighbour;
    }

    @Override
    public String toString() {
        return this.hundredsOfSpecies + ";" + this.firstNeighbour + ";" + this.secondNeighbour;
    }
}
