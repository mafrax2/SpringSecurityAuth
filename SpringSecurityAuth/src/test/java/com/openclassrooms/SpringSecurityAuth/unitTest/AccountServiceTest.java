package com.openclassrooms.SpringSecurityAuth.unitTest;

import com.openclassrooms.SpringSecurityAuth.model.Account;
import com.openclassrooms.SpringSecurityAuth.model.Transaction;
import com.openclassrooms.SpringSecurityAuth.repository.AccountRepository;
import com.openclassrooms.SpringSecurityAuth.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private AccountService accountService;

    private List<Account> accounts;


    private static Account createAccount(int id, String firstName, String lastName, String password, String email, String role, boolean enabled ){

        Account account = new Account();
        account.setId(id);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setPassword(password);
        account.setEmail(email);
        account.setRole(role);
        account.setEnabled(enabled);

        account.setFriends(new ArrayList<Account>());
        account.setTransactions(new ArrayList<Transaction>());

        return account;
    }

    private static List<Account> createAccounts(){
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(createAccount(1,"marc", "mon", "pass", "email0", "USER", true));
        accounts.add(createAccount(2,"aur", "man", "pass", "email1", "USER", true));
        accounts.add(createAccount(3,"truc", "mon", "pass", "email2", "USER", true));
        accounts.add(createAccount(4,"machin", "mon", "pass", "email3", "USER", true));
        accounts.add(createAccount(5,"bidule", "mon", "pass", "email4", "USER", true));

        return accounts;
    }

    private static void addFriends(Account account, List<Account> accounts){

        List<Account> friends = account.getFriends();
        List<Account> toAdd = new ArrayList<>();
        toAdd.addAll(accounts);
        toAdd.remove(account);
        friends.addAll(accounts);

    }

    @BeforeEach
    private void setUpPerTest() throws Exception {

        accounts = createAccounts();
        accountService = new AccountService(accountRepository);

        addFriends(accounts.get(0), accounts);

    }


    @Test
    public void getAccountByIdTest(){

        when(accountRepository.findById(1)).thenReturn(Optional.ofNullable(accounts.get(0)));
        Optional<Account> account = accountService.getAccount(1);

        Account account1 = createAccount(1, "marc", "mon", "pass", "email0", "USER", true);

        assertEquals(account.get(), account1);

    }


    @Test
    public void getAccountByEmailTest(){
        when(accountRepository.findByEmail("email0")).thenReturn(Optional.ofNullable(accounts.get(0)));
        Optional<Account> accountByEmail = accountService.getAccountByEmail("email0");

        Account account1 = createAccount(1, "marc", "mon", "pass", "email0", "USER", true);

        assertEquals(accountByEmail.get(), account1);

    }


    @Test
    public void getAccounts(){
        when(accountRepository.findAll()).thenReturn(accounts);
        Iterable<Account> serviceAccounts = accountService.getAccounts();

        assertIterableEquals(accounts, serviceAccounts);
    }

    @Test
    public void saveAccountTest(){
        when(accountRepository.save(any(Account.class))).then(returnsFirstArg());
        Account account = accountService.saveAccount(accounts.get(0));

        Account account1 = createAccount(1, "marc", "mon", "pass", "email0", "USER", true);

        assertEquals(account, account1);

    }

    @Test
    public void getFriendsTest(){
        when(accountRepository.findById(1)).thenReturn(Optional.ofNullable(accounts.get(0)));

        Account account1 = createAccount(1, "marc", "mon", "pass", "email0", "USER", true);
        addFriends(account1, accounts);

        List<Account> friends = accountService.getFriends(1);

        assertIterableEquals(account1.getFriends(), friends);

    }



}
