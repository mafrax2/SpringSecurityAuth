package com.openclassrooms.SpringSecurityAuth.configuration;

import com.openclassrooms.SpringSecurityAuth.model.Account;
import com.openclassrooms.SpringSecurityAuth.model.Transaction;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class MyUserDetails implements UserDetails {


    private String userName;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;


    private Integer id;
    private String firstName;
    private String lastName;

    private List<Account> friends;
    private List<Transaction> transactions;

    public MyUserDetails(Account account) {

        this.id = account.getId();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.friends = account.getFriends();
        this.transactions = account.getTransactions();

        this.userName = account.getEmail();
        this.password = account.getPassword();
        this.active = account.isEnabled();
        this.authorities = Arrays.asList(new SimpleGrantedAuthority(account.getRole()));
    }

    public MyUserDetails() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Account> getFriends() {
        return friends;
    }

    public void setFriends(List<Account> friends) {
        this.friends = friends;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }


    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
