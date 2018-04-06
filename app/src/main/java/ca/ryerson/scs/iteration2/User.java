package ca.ryerson.scs.iteration2;

public class User {

    private String email;
    private String password;
    private String role;
    private int associatedId;

    public User(String email, String password, String role, int associatedId) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.associatedId = associatedId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(int associatedId) {
        this.associatedId = associatedId;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", associatedId=" + associatedId +
                '}';
    }
}
