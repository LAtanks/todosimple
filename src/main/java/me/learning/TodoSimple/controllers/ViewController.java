package me.learning.TodoSimple.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@Controller
public class ViewController {

    @GetMapping("/home")
    public String home(ModelMap model) {
        model.addAttribute("message", "Bem-vindo ao Thymeleaf!");
        return "view/signPage";
    }
}
