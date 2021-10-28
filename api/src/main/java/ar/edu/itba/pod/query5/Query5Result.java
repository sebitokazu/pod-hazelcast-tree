package ar.edu.itba.pod.query5;

public class Query5Result {
    private int tensOfSpecies;
    private String firstStreet;
    private String secondStreet;

    public Query5Result(int tensOfSpecies, String firstStreet, String secondStreet) {
        this.tensOfSpecies = tensOfSpecies;
        if(firstStreet.compareTo(secondStreet)>=0){
            this.firstStreet = firstStreet;
            this.secondStreet = secondStreet;
        }else {
            this.firstStreet = secondStreet;
            this.secondStreet = firstStreet;
        }
    }

    public int getTensOfSpecies() {
        return tensOfSpecies;
    }

    public String getFirstStreet() {
        return firstStreet;
    }

    public String getSecondStreet() {
        return secondStreet;
    }

    @Override
    public String toString() {
        return tensOfSpecies + ";" + firstStreet + ";" + secondStreet;
    }
}
