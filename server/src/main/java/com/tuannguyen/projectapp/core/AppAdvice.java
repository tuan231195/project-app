package com.tuannguyen.projectapp.core;

import com.tuannguyen.projectapp.core.exception.ApiException;
import com.tuannguyen.projectapp.core.exception.InvalidInputException;
import com.tuannguyen.projectapp.core.exception.NotFoundException;
import com.tuannguyen.projectapp.core.json.Error;
import com.tuannguyen.projectapp.core.model.ViewModel;
import com.tuannguyen.projectapp.util.model.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

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

    /*
     *   Page Exception handler
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError500(HttpServletRequest request, Exception e) {
        Logger.getLogger(getClass().getName()).error("Request: " + request.getRequestURL() + " raised ", e);
        return new ViewModel().view("error/500")
                .addObject("appTitle", appTitle())
                .addObject("appDevPath", appDevPath());
    }


    /*
     *   API Exception handler
     */

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Error> handleInvalidInput(HttpServletRequest request, InvalidInputException e) {
        Logger.getLogger(getClass().getName()).error("Request: " + request.getRequestURL() + " raised ", e);
        return new ResponseEntity<>(new Error(e.getMessage()).errors(e.getErrors()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> handleNotFound(HttpServletRequest request, NotFoundException e) {
        Logger.getLogger(getClass().getName()).error("Request: " + request.getRequestURL() + " raised ", e);
        return  new ResponseEntity<>(new Error("Invalid " + e.getField()).error(e.getTable() + " " + e.getValue() + " not found."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Error> handleApiException(HttpServletRequest request, ApiException e) {
        Logger.getLogger(getClass().getName()).error("Request: " + request.getRequestURL() + " raised ", e);
        return  new ResponseEntity<>(new Error(e.getTitle()).error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
