package com.fatez.labbd.CampusQuest.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String usuario,
                        @RequestParam String senha,
                        HttpSession session,
                        Model model) {

        if (autenticar(usuario, senha)) {
            session.setAttribute("usuarioAutenticado", true);
            session.setAttribute("usuario", usuario);
            return "redirect:/curiosidade";
        } else {
            model.addAttribute("erro", "Usuário ou senha inválidos");
            return "login";
        }
    }

    private boolean autenticar(String usuario, String senha) {
        return "admin".equals(usuario) && "123".equals(senha);
    }
}