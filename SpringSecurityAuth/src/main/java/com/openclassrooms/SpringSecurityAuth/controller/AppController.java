package com.openclassrooms.SpringSecurityAuth.controller;

import com.openclassrooms.SpringSecurityAuth.model.Account;
import com.openclassrooms.SpringSecurityAuth.model.Transaction;
import com.openclassrooms.SpringSecurityAuth.service.AccountService;
import com.openclassrooms.SpringSecurityAuth.service.LoginService;
import com.openclassrooms.SpringSecurityAuth.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.*;

@Controller
@Data
@AllArgsConstructor
public class AppController

{

    private LoginService loginService;
    private AccountService accountService;
    private TransactionService transactionService;
    private final PasswordEncoder passwordEncoder;


    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public String profile(Model model, Principal user) {

        model.addAttribute("user", user);

        Account userAccount = loginService.getUserAccount(user);

        model.addAttribute("userAccount", userAccount);
        List<Account> friends = new ArrayList<>();
        if (userAccount!=null ){
         friends = accountService.getFriends(userAccount.getId());
        }

        model.addAttribute("friends", friends);
        Float balance = transactionService.getBalance(userAccount);
        model.addAttribute("balance", balance);

        return "profile";
    }

    @RequestMapping(value = {"/transfer"}, method = RequestMethod.GET)
    public String transfer(Model model, Principal user) {

        model.addAttribute("user", user);
        Account userAccount = loginService.getUserAccount(user);
        List<Account> friends = accountService.getFriends(userAccount.getId());

        model.addAttribute("friends", friends);

        Transaction transaction = new Transaction();

        model.addAttribute("Transaction", transaction);

        List<Transaction> receivedTransactions = transactionService.getReceivedTransactions(userAccount.getId());
        List<Transaction> sentTransactions = transactionService.getSentTransactions(userAccount.getId());

        model.addAttribute("sentTransactions", sentTransactions);
        model.addAttribute("receivedTransactions", receivedTransactions);

        Float balance = transactionService.getBalance(userAccount);
        model.addAttribute("balance", balance);


        return "transfer";
    }

    @PostMapping("/saveTransaction")
    public ModelAndView getTransaction(@ModelAttribute Transaction transaction, Principal principal){

        transaction.setEmissionDate(new Date());
        Account userAccount = loginService.getUserAccount(principal);
        transaction.setSenderAccount(userAccount);
        float v = (float) (transaction.getAmount() * 0.0005);
        transaction.setAmount(transaction.getAmount()- v);
        transactionService.saveTransaction(transaction);

        return new ModelAndView("redirect:/transfer");

    }

    @GetMapping("/contact")
    public String contact(Model model, Principal user){

        model.addAttribute("user", user);
        Account userAccount = loginService.getUserAccount(user);

        List<Account> listAccount = (List<Account>) accountService.getAccounts();
        listAccount.remove(userAccount);

        List<Account> friends = new ArrayList<>();
        if (userAccount.getId()!=null){
            friends = accountService.getFriends(userAccount.getId());
        }

        listAccount.removeAll(friends);

        model.addAttribute("friends", friends);

        model.addAttribute("accounts", listAccount);

        model.addAttribute("userAccount", userAccount);


        Account connection = new Account();
        model.addAttribute("connection", connection);

        return "contact";
    }

    @PostMapping("/saveConnection")
    public ModelAndView saveConnection(@ModelAttribute Account account, Principal principal){


        Account newFriend = accountService.getAccount(account.getId()).get();

        Account userAccount = loginService.getUserAccount(principal);
        List<Account> friends = accountService.getFriends(userAccount.getId());
        friends.add(newFriend);
        userAccount.setFriends(friends);
        accountService.saveAccount(userAccount);

        return new ModelAndView("redirect:/contact");

    }

}