package com.andrzej.bookDatabase.Model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Name can not be empty")
    private String name;
    @Column(name = "lastName")
    @NotEmpty(message = "Last name can not be empty")
    private String lastName;
    @Column(name = "password")
    @NotEmpty(message = "Password can not be empty")
    @Length(min = 5, message = "Password must be at list five characters")
    private String password;
    @Email()
    @Column(name = "email")
    private String email;
    @Column(name = "active")
    private int active;
    @Column(name = "roles")
    @ManyToMany()
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(@NotEmpty(message = "Name can not be empty") String name, @NotEmpty(message = "Last name can not be empty") String lastName, @NotEmpty(message = "Password can not be empty") @Length(min = 5, message = "Password must be at list five characters") String password, @Email() String email, int active, Set<Role> roles) {
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.active = active;
        this.roles = roles;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
