package com.andrzej.bookDatabase.Model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
@SequenceGenerator(name = "user_gen", sequenceName = "user_seq",initialValue = 2)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_gen")
    @Column(name = "user_id")
    private long id;
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
    private boolean active;
    @Column(name = "roles")
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public User() {
    }

    public User(@NotEmpty(message = "Name can not be empty") String name, @NotEmpty(message = "Last name can not be empty") String lastName, @NotEmpty(message = "Password can not be empty") @Length(min = 5, message = "Password must be at list five characters") String password, @Email() String email, boolean active, List<Role> roles) {
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.active = active;
        this.roles = roles;
    }

    public long getId() {
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

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
