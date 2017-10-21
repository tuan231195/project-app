package com.tuannguyen.projectapp.core.model;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.Map;

public class ViewModel extends ModelAndView {
    public ViewModel scripts(List<String> scripts) {
        addObject("scripts", scripts);
        return this;
    }

    public ViewModel angularApp(String appName) {
        addObject("angularApp", appName);
        return this;
    }

    public ViewModel angularValues(Map<String, String> angularValues) {
        addObject("angularValues", angularValues);
        return this;
    }

    public ViewModel angularModuleValues(Map<String, Map<String, String>> angularModuleValues) {
        addObject("angularModuleValues", angularModuleValues);
        return this;
    }

    public ViewModel view(String viewName) {
        setViewName(viewName);
        return this;
    }

    public ViewModel view(View view) {
        setView(view);
        return this;
    }
}