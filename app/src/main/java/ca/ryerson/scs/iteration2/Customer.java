package ca.ryerson.scs.iteration2;

public class Customer{

    private String name;
    private String email;
    private int consecutivePayment;

    public Customer(int id, String name, String email, String password, int consecutivePayment) {

        this.name = name;
        this.email = email;
        this.consecutivePayment = consecutivePayment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getConsecutivePayment() {
        return consecutivePayment;
    }

    public void setConsecutivePayment(int consecutivePayment) {
        this.consecutivePayment = consecutivePayment;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", consecutivePayment=" + consecutivePayment +
                '}';
    }

}
