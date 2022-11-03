package com.fileUpload.app.model;

import java.util.Objects;

/**
 * Client
 *
 */
public class Client {

    private String name;
    private String profession;

    public Client(String name, String profession) {
        this.name = name;
        this.profession = profession;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return name.equals(client.name) && profession.equals(client.profession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, profession);
    }
}
