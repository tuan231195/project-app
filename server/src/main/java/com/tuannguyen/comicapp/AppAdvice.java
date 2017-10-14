package com.tuannguyen.comicapp;

import com.tuannguyen.comicapp.util.ViewModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@PropertySources({@PropertySource("classpath:global.properties")})
public class AppAdvice {

    @Value("${app.title}")
    private String title;

    @Value("${app.dev-path}")
    private String devPath;

    @Value("${isDev:#{null}}")
    private Boolean isDev;

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

    @ExceptionHandler(NoHandlerFoundException.class)
    public ViewModel handleError404(HttpServletRequest request, Exception e) {
        Logger.getLogger(getClass().getName()).error("Request: " + request.getRequestURL() + " raised ", e);
        return new ViewModel().view("error/404");
    }

    @ExceptionHandler(Exception.class)
    public ViewModel handleError500(HttpServletRequest request, Exception e) {
        Logger.getLogger(getClass().getName()).error("Request: " + request.getRequestURL() + " raised ", e);
        return new ViewModel().view("error/500");
    }
}
