package com.tuannguyen.projectapp.auth;

import com.tuannguyen.projectapp.util.ViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public ViewModel login() {
        return new ViewModel()
                .view("auth/login")
                .angularApp("tn.auth");
    }

    @RequestMapping("/signup")
    public ViewModel signup() {
        return new ViewModel()
                .view("auth/signup")
                .angularApp("tn.auth");
    }
}
