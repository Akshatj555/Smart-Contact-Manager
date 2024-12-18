package com.scm.scm20.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.scm20.entities.User;
import com.scm.scm20.helpers.Helper;
import com.scm.scm20.services.UserService;

@ControllerAdvice
public class RootController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication){

        if(authentication == null){
            return;
        }

        System.out.println("Adding logged in User Information to the model");
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User Logged In: {}", username);

        // As we have got the username, now we can fetch the data from DB
        // getting user from Database

        User user = userService.getUserByEmail(username);


            System.out.println(user.getName());
            System.out.println(user.getEmail());
    
            model.addAttribute("loggedInUser", user);
            // here the loggedInUser is the key for using the User object in the Frontend thatis profile page for now    
    }
}
