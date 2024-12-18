package com.scm.scm20.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.scm20.entities.Providers;
import com.scm.scm20.entities.User;
import com.scm.scm20.helpers.AppConstants;
import com.scm.scm20.repository.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    private Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
                logger.info("OAuthAuthenticationSuccessHandler");

                //identifying the provider

                var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

                String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

                logger.info(authorizedClientRegistrationId);

                var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

                oauthUser.getAttributes().forEach((key, value) -> {
                    logger.info("{} : {}", key, value);
                });

                //Setting values for our user

                //Setting the common values
                User user = new User();
                user.setUserId(UUID.randomUUID().toString());
                user.setRoleList(List.of(AppConstants.ROLE_USER));
                user.setEmailVerified(true);
                user.setEnabled(true);
                user.setPassword("dummy");

                //will set up client type specific values in the below conditions

                if(authorizedClientRegistrationId.equalsIgnoreCase("google")){

                    //google attributes

                    user.setEmail(oauthUser.getAttribute("email").toString());
                    user.setProfilePic(oauthUser.getAttribute("picture").toString());
                    user.setName(oauthUser.getAttribute("name").toString());
                    user.setProviderUserId(oauthUser.getName());
                    user.setProvider(Providers.GOOGLE);
                    user.setAbout("This account is created using Google");

                }else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {

                    //github attributes

                    String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString() : oauthUser.getAttribute("login").toString() + "@gmail.com";
                    String picture = oauthUser.getAttribute("avatar_url").toString();
                    String name = oauthUser.getAttribute("login").toString();
                    String providerUserId = oauthUser.getName();

                    user.setEmail(email);
                    user.setProfilePic(picture);
                    user.setName(name);
                    user.setProviderUserId(providerUserId);
                    user.setProvider(Providers.GITHUB);
                    user.setAbout("This account is created using Github");
                    
                }else if (authorizedClientRegistrationId.equalsIgnoreCase("linkedin")) {
                    
                }else{
                    logger.info("OAuthAuthenticationSuccessHandler: Unknown provider");
                }


                /*

                DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

                // logger.info("User Name is: {}", user.getName());

                // user.getAttributes().forEach((key, value) ->{
                //     logger.info("{} => {}", key, value);
                // });

                // logger.info(user.getAuthorities().toString());

                String email = user.getAttribute("email");
                String name = user.getAttribute("name");
                String picture = user.getAttribute("picture");

                // Create user and save in database

                User user1 = new User();
                user1.setEmail(email);
                user1.setName(name);
                user1.setProfilePic(picture);
                user1.setPassword("password");
                user1.setUserId(UUID.randomUUID().toString());
                user1.setProvider(Providers.GOOGLE);
                user1.setEnabled(true);
                user1.setEmailVerified(true);
                user1.setProviderUserId(user.getName());
                user1.setRoleList(List.of(AppConstants.ROLE_USER));
                user1.setAbout("This account is created using google oauth...");


                // Need to save data to database

                User user2 = userRepo.findByEmail(email).orElse(null);
                if(user2 == null){
                    userRepo.save(user1);
                    logger.info("User Saved: {}", email);
                }

                */

                // logger.info("User details: {}", user.toString());
                // logger.info("userRepo.findByEmail: {}", userRepo.findByEmail(user.getEmail()));

                User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
                // logger.info("User2 details: {}", user2);


                if(user2 == null){
                    userRepo.save(user);
                    logger.info("User Saved: {}", user.getEmail());
                    // logger.info("User Saved: {}", user2.toString());
                }

                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

    }

}