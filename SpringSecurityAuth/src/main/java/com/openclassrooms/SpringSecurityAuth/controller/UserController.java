package com.openclassrooms.SpringSecurityAuth.controller;

import com.openclassrooms.SpringSecurityAuth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/principal")
    public Principal retrievePrincipal(Principal principal) {

        System.out.println(principal);
        return principal;
    }

    @RequestMapping("/home/*")
    public String getUserInfo(Principal user)
    {
        StringBuffer userInfo = new StringBuffer();
        Map<String, Object> userDetails = loginService.getUserDetails(user);
        if (user instanceof UsernamePasswordAuthenticationToken){
            userInfo.append(loginService.getUsernamePasswordLoginInfo(user));
        } else if (user instanceof OAuth2AuthenticationToken){
            userInfo.append(loginService.getOAuth2LoginInfo(user));
        }

        return userInfo.toString();
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping("/home/admin")
    public String getAdmin()
    {
        return "Welcome Admin";
    }
}
