package com.openclassrooms.SpringSecurityAuth.service;

import com.openclassrooms.SpringSecurityAuth.configuration.MyUserDetails;
import com.openclassrooms.SpringSecurityAuth.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class LoginService {

    @Autowired
    private AccountService accountService;

    private final OAuth2AuthorizedClientService auithorizedClientService;

    public LoginService(OAuth2AuthorizedClientService auithorizedClientService) {
        this.auithorizedClientService = auithorizedClientService;
    }

    public Account getUserAccount(Principal user){

        if (user instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) user;

            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) token.getPrincipal();
            Map<String, Object> attributes = defaultOAuth2User.getAttributes();
            String email = (String) attributes.get("email");
            Optional<Account> optionalAccount = accountService.getAccountByEmail(email);


            return optionalAccount.orElseGet(() -> {
                Account account = new Account();
                account.setFirstName((String) attributes.get("given_name"));
                account.setLastName((String) attributes.get("family_name"));
                account.setRole("ROLE_USER");
                account.setEmail(email);
                account.setEnabled(false);
                accountService.saveAccount(account);
                return account;
            });

        } else if (user instanceof UsernamePasswordAuthenticationToken){
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;
            MyUserDetails u = (MyUserDetails) token.getPrincipal();
            return new Account(u);
        }
        return null;
    }

    public Map<String, Object> getUserDetails(Principal user){

        Map<String, Object> userDetails = new HashMap<>();

        if (user instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) user;

            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) token.getPrincipal();
            userDetails = defaultOAuth2User.getAttributes();

        } else if (user instanceof UsernamePasswordAuthenticationToken){
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;

            if(token.isAuthenticated()){
                MyUserDetails u = (MyUserDetails) token.getPrincipal();
                userDetails.put("authenticated", true);
                userDetails.put("login", u.getUsername());
                userDetails.put("id", u.getId());
                userDetails.put("firstName", u.getFirstName());
                userDetails.put("lastName", u.getLastName());
                List<Account> friends = accountService.getFriends(u.getId());
                userDetails.put("friends", friends);
            } else {
                userDetails.put("authenticated", false);
            }

        }
        return userDetails;
    }

    public StringBuffer getOAuth2LoginInfo(Principal user){
        StringBuffer protectedInfo = new StringBuffer();

        Map<String, Object> userDetails = getUserDetails(user);
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) user;

        OAuth2AuthorizedClient client = this.auithorizedClientService.loadAuthorizedClient(token.getAuthorizedClientRegistrationId(), token.getName());

        String tokenValue = client.getAccessToken().getTokenValue();
        String name = (String) (userDetails.get("login") != null ? userDetails.get("login") : userDetails.get("name"));
        protectedInfo.append("Welcome "+ name + "<br></br>");
        protectedInfo.append("email "+ userDetails.get("email") + "<br></br>");
        protectedInfo.append("token "+ tokenValue + "<br></br>");


        OAuth2User principal = ((OAuth2AuthenticationToken) user).getPrincipal();
        OidcIdToken idToken = getIdToken(principal);

        if(idToken != null) {

            protectedInfo.append("idToken value: " + idToken.getTokenValue()+"<br><br>");
            protectedInfo.append("Token mapped values <br><br>");

            Map<String, Object> claims = idToken.getClaims();

            for (String key : claims.keySet()) {
                protectedInfo.append("  " + key + ": " + claims.get(key)+"<br>");
            }
        }

        return protectedInfo;
    }

    public StringBuffer getUsernamePasswordLoginInfo(Principal user){
        StringBuffer userNameInfo = new StringBuffer();
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;

        if(token.isAuthenticated()){
            MyUserDetails u = (MyUserDetails) token.getPrincipal();
            userNameInfo.append("Welcome "+ u.getUsername());
        } else {
            userNameInfo.append("NA");
        }

        return userNameInfo;
    }

    private OidcIdToken getIdToken(OAuth2User principal){
        if(principal instanceof DefaultOidcUser){
            DefaultOidcUser defaultOidcUser = (DefaultOidcUser) principal;
            return defaultOidcUser.getIdToken();
        }


        return null;
    }

}
