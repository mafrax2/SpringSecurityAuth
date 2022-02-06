package com.openclassrooms.SpringSecurityAuth.service;

import com.openclassrooms.SpringSecurityAuth.model.Account;
import com.openclassrooms.SpringSecurityAuth.model.Transaction;
import com.openclassrooms.SpringSecurityAuth.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {


    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> getAccount(final int id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Iterable<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    public Account saveAccount(Account account) {
        Account savedAccount;

        if(account.getId() == null) {
            // Si l'id est nul, alors c'est un nouvel employé.
            savedAccount = accountRepository.save(account);
        } else {
            savedAccount = accountRepository.save(account);
        }

        return savedAccount;
    }

    @Transactional
    public List<Account> getFriends(Integer id){
        Optional<Account> byId = accountRepository.findById(id);
        byId.orElseThrow(() -> new UsernameNotFoundException("Can't find user with id: " + id));

        List<Account> friends = byId.get().getFriends();

        return friends;

    }


}
