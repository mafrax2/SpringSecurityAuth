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
        if (userAccount.getId()!=null){
         friends = accountService.getFriends(userAccount.getId());
        }

        model.addAttribute("friends", friends);

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


        return "transfer";
    }

    @PostMapping("/saveTransaction")
    public ModelAndView getTransaction(@ModelAttribute Transaction transaction, Principal principal){

         System.out.println(transaction);
        transaction.setEmissionDate(new Date());
        Map<String, Object> userDetails = loginService.getUserDetails(principal);
        Account account = accountService.getAccountByEmail((String) userDetails.get("email")).get();
        transaction.setSenderAccount(account);
        transactionService.saveTransaction(transaction);

        return new ModelAndView("redirect:/");

    }

    @GetMapping("/contact")
    public String contact(Model model, Principal user){

        model.addAttribute("user", user);

        Account userAccount = loginService.getUserAccount(user);

        Iterable<Account> listAccount = accountService.getAccounts();
        model.addAttribute("accounts", listAccount);

        model.addAttribute("userAccount", userAccount);
        List<Account> friends = new ArrayList<>();
        if (userAccount.getId()!=null){
            friends = accountService.getFriends(userAccount.getId());
        }

        model.addAttribute("friends", friends);

        Account connection = new Account();
        model.addAttribute("connection", connection);

        return "contact";
    }

    @PostMapping("/saveConnection")
    public ModelAndView saveConnection(@ModelAttribute Account account, Principal principal){

        System.out.println(account);
        Account newFriend = accountService.getAccount(account.getId()).get();
        Map<String, Object> userDetails = loginService.getUserDetails(principal);
        Account userAccount = accountService.getAccountByEmail((String) userDetails.get("email")).get();
        userAccount.getFriends().add(newFriend);
        accountService.saveAccount(userAccount);

        return new ModelAndView("redirect:/contact");

    }

}