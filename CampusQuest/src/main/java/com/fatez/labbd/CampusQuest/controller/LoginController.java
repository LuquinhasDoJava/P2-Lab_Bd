package com.fatez.labbd.CampusQuest.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/cadastraTipo")
    public String carregarPagina(){
        return "login";
    }

    @PostMapping("/login")
    public String verificarLogin(@RequestParam String usuario, @RequestParam String senha, HttpSession session){
        session.setAttribute("usuarioAutenticado", true);
        return "redirect:/curiosidade";
/*        if ("admin".equals(usuario) && "Jej-W+q%".equals(senha)){
            session.setAttribute("usuarioAutenticado", true);
            return "redirect:/curiosidade";
        } else {
            return "redirect:/login?erro=true";
        }*/
    }

    @GetMapping("/login")
    public String erroDeLogin(@RequestParam(value = "erro", required = false) String erro, Model model){
        if (erro != null){
            model.addAttribute("erro","Usuario ou senha invalidos!!");
        }
        return "login";
    }
}
