package com.scm.scm20.helpers;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){

        if(authentication instanceof OAuth2AuthenticationToken){

            var authenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = authenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User) authentication.getPrincipal();
            String username = "";

            if(clientId.equalsIgnoreCase("google")){

                System.out.println("Getting email from Google");
                username = oauth2User.getAttribute("email").toString();

            }else if(clientId.equalsIgnoreCase("github")){

                System.out.println("Getting email from Github");
                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString() + "@gmail.com";
            }

            return username;

        }else{
            System.out.println("Getting data from local database");
            return authentication.getName();
        }


    }
}
