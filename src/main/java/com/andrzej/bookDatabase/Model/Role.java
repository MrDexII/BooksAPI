package com.andrzej.bookDatabase.Model;

import javax.persistence.*;

@Entity(name = "roles")
@SequenceGenerator(name = "role_gen", sequenceName = "role_seq", initialValue = 3)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "role_gen")
    @Column(name = "role_id")
    private int id;
    @Column(name = "role")
    private String role;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
