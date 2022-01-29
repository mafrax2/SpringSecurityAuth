package com.openclassrooms.SpringSecurityAuth.controller;

import javax.annotation.security.RolesAllowed;

import com.openclassrooms.SpringSecurityAuth.model.Account;
import com.openclassrooms.SpringSecurityAuth.service.AccountService;
import com.openclassrooms.SpringSecurityAuth.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

@Controller
@Data
@AllArgsConstructor
public class LoginController

{

    private LoginService loginService;
    private AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    private Account account;

    @RequestMapping(value = {"/" , "/home"}, method = RequestMethod.GET)
    public String home(Model model, Principal user) {

        if(user instanceof OAuth2AuthenticationToken){
            this.account = loginService.getUserAccount(user);
        }

        model.addAttribute("user", user);
        model.addAttribute("userdetails", loginService.getUserDetails(user));

        Iterable<Account> listAccount = accountService.getAccounts();
        model.addAttribute("accounts", listAccount);

        return "home";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String viewRegister(Model model) {
        Account account = new Account();
        model.addAttribute("Account", account);
        return "register";
    }

    @PostMapping("/saveAccount")
    public ModelAndView getRegistration(@ModelAttribute Account account){
        account.setRole("ROLE_USER");
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        accountService.saveAccount(account);

        return new ModelAndView("redirect:/");
    }



}