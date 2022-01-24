package com.openclassrooms.SpringSecurityAuth.configuration;

import com.openclassrooms.SpringSecurityAuth.model.Account;
import com.openclassrooms.SpringSecurityAuth.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> byEmail = repository.findByEmail(email);

        byEmail.orElseThrow(() -> new UsernameNotFoundException("Not found "+ email));

        MyUserDetails myUserDetails = byEmail.map(MyUserDetails::new).get();
        return myUserDetails;

    }
}
