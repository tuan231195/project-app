package com.tuannguyen.comicapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@PropertySources({@PropertySource("classpath:global.properties")})
public class AppAdvice {

    @Value("${app.title}")
    private String title;

    @Value("${app.dev-path}")
    private String devPath;

    @Value("${isDev:#{null}}")
    private Boolean isDev;

    @Value("${app.angular-app}")
    private String angularApp;


    @ModelAttribute("appTitle")
    public String appTitle() {
        return title;
    }

    @ModelAttribute("appDevPath")
    public String appDevPath() {
        if (isDev == Boolean.TRUE) {
            return devPath;
        } else {
            return "";
        }
    }

    @ModelAttribute("angularApp")
    public String angularApp(){
        return angularApp;
    }
}
