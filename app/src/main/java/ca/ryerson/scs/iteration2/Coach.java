package ca.ryerson.scs.iteration2;

public class Coach {

    private String name;
    private String rate;

    public Coach(String name, String rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "name='" + name + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }
}
