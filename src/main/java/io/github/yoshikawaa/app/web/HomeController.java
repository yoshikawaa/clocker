package io.github.yoshikawaa.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.yoshikawaa.app.domain.DateService;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private DateService service;
    
    @GetMapping
    public String home(Model model) {
        model.addAttribute("remainingTime", service.getRemainingTime());
        return "home";
    }
}
