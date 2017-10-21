package com.tuannguyen.projectapp.auth.controllers;

import com.tuannguyen.projectapp.auth.service.UserService;
import com.tuannguyen.projectapp.core.model.ViewModel;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public ModelAndView login(Model model) {
        if (userService.isAuthenticated()) {
            return new ViewModel().view("redirect:/");
        }
        boolean verifySuccess = (Boolean) model.asMap().getOrDefault("verifySuccess", false);
        return new ViewModel()
                .view("auth/login")
                .angularApp("tn.auth")
                .addObject("verifySuccess", verifySuccess);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}
