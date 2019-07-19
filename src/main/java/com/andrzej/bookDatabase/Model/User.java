package com.andrzej.bookDatabase.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    Set<Role> roles;
}
