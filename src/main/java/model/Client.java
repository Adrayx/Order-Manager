package model;

/**
 * Aceasta este clasa prin care se memorează datele unui client
 */

public class Client {
    private int id;
    private String name;
    private String email;
    private String address;

    public Client(){}

    public Client(int id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void getDeclaredFields(){

    }
    @Override
    public String toString() {
        return id + ") Client name: " + name + ", address: " + address + ", email: " + email;
    }
}
