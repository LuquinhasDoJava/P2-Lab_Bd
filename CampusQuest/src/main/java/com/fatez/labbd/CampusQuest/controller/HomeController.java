package com.fatez.labbd.CampusQuest.controller;

import com.fatez.labbd.CampusQuest.service.ClubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ClubeService clubeService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("listaClubes", clubeService.listarTodos());
        return "candidato/escolher-time";
    }
}