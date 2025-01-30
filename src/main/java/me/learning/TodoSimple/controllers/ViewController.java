package me.learning.TodoSimple.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

@Controller
public class ViewController {

    @RequestMapping(value = "css/sign.css", method = RequestMethod.GET)
    public String signCSS(Model model, HttpServletResponse response) {
        return "css/sign.css";
    }

    @RequestMapping(value = "css/home.css", method = RequestMethod.GET)
    public String homeCSS(Model model, HttpServletResponse response) {
        return "css/home.css";
    }

    @RequestMapping(value = "signPage", method = RequestMethod.GET)
    public ModelAndView signPage(ModelMap model) {
        ModelAndView mv = new ModelAndView("signPage");
        return mv;
    }

    @RequestMapping(value = "homePage", method = RequestMethod.GET)
    public ModelAndView homePage(ModelMap model) {
        ModelAndView mv = new ModelAndView("homePage");
        return mv;
    }

    @RequestMapping(value = "js/sign.js", method = RequestMethod.GET)
    public String SingJS(Model model, HttpServletResponse response) {
        return "js/sign.js";
    }
}
