package com.openclassrooms.SpringSecurityAuth.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openclassrooms.SpringSecurityAuth.configuration.MyUserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "enabled",nullable = false, columnDefinition = "BOOLEAN", length = 1)
    private boolean enabled;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="connection",
            joinColumns=@JoinColumn(name="personId"),
            inverseJoinColumns=@JoinColumn(name="friendId")
    )
    private List<Account> friends;


    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private List<Transaction> transactions;


    public Account() {
    }

    public Account(Account account) {
    }

    public Account(MyUserDetails user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.email = user.getUsername();
    }
}


