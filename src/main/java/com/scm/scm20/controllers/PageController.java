package com.scm.scm20.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.scm20.entities.User;
import com.scm.scm20.forms.UserForm;
import com.scm.scm20.helpers.Message;
import com.scm.scm20.helpers.MessageType;
import com.scm.scm20.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model){
        System.out.println("Home page handler");
        // model.addAttribute("name", "SubString Technologies");
        // model.addAttribute("youtubeChannel", "LearnCodingWithAkshat");
        // model.addAttribute("githubRepo", "https://github.com/akshatj555");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(){
        System.out.println("About Page loading...");
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage(){
        System.out.println("Services Page loading...");
        return "services";
    }

    @GetMapping("/contact")
    public String contactPage(){
        return "contact";
    }

    // this is to show the login page

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    // registration page

    @GetMapping("/register")
    public String registerPage(Model model){

        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        // userForm.setName("Akshat");
        // userForm.setAbout("Tell us something about yourself...");

        return "register";
    }

    // processing register

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult,HttpSession session){
        System.out.println("Processing registration");

        // fetch form data
        System.out.println(userForm);
        // User form

        // validate form data

        if (bindingResult.hasErrors()) {
            return "register";
        }

        // save to database

        // user Service

        // User user = User.builder()
        // .name(userForm.getName())
        // .about(userForm.getAbout())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("")
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://w7.pngwing.com/pngs/177/551/png-transparent-user-interface-design-computer-icons-default-stephen-salazar-graphy-user-interface-design-computer-wallpaper-sphere-thumbnail.png");

        User savedUser = userService.saveUser(user);

        System.out.println("User Saved");
        //message= "Registration Successful"

        Message message = Message.builder().content("Registration Successful!").type(MessageType.green).build();

        // add the message
        session.setAttribute("message", message);

        // redirect to login page
        return "redirect:/register";
    }
}
