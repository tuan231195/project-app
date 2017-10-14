package com.tuannguyen.projectapp.user;

import com.google.common.collect.ImmutableMap;
import com.tuannguyen.projectapp.util.ViewModel;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class UserController {

    @RequestMapping("/")
    public ModelAndView welcome() {
        Map<String, String> angularValues = ImmutableMap.<String, String>builder()
                .put("value1", "1")
                .put("value2", "2")
                .build();
        Map<String, Map<String, String>> angularModuleValues = ImmutableMap.<String, Map<String, String>>builder()
                .put("tn.users", ImmutableMap.<String, String>builder()
                        .put("moduleValue1", "module1")
                        .build())
                .put("tn.app", ImmutableMap.<String, String>builder()
                        .put("moduleValue2", "module2")
                        .build())
                .build();

        return new ViewModel()
                .view("user/user")
                .angularApp("tn.users")
                .angularValues(angularValues)
                .angularModuleValues(angularModuleValues);
    }

}