package com.fatez.labbd.CampusQuest.controller;

import com.fatez.labbd.CampusQuest.entity.Curiosidade;
import com.fatez.labbd.CampusQuest.service.ClubeService;
import com.fatez.labbd.CampusQuest.service.CuriosidadeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clube")
public class ClubeController {

    @Autowired
    private ClubeService clubeService;

    @Autowired
    private CuriosidadeService curiosidadeService;

    @GetMapping
    public String acessarPagina(HttpSession session, Model model){
        if (session.getAttribute("usuarioAutenticado") == null || !Boolean.TRUE.equals(session.getAttribute("usuarioAutenticado"))) {
            return "redirect:/cadastraTipo";
        }

        model.addAttribute("listaCuriosidade", curiosidadeService.listarTodos());

        if (!model.containsAttribute("curiosidade")) {
            model.addAttribute("curiosidade", new Curiosidade());
        }
        model.addAttribute("listaClubes", clubeService.listarTodos());

        return "clube/cadastro";
    }

    @GetMapping("/editar/{id}")
    public String editarCuriosidade(@PathVariable int id, Model model, HttpSession session){
        if (session.getAttribute("usuarioAutenticado") == null ||
                !Boolean.TRUE.equals(session.getAttribute("usuarioAutenticado"))) {
            return "redirect:/cadastraTipo";
        }

        try{
            Curiosidade curiosidade = curiosidadeService.buscarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Curiosidade não encontrada!"));

            model.addAttribute("curiosidade", curiosidade);
            model.addAttribute("listaCuriosidade", curiosidadeService.listarTodos());
            model.addAttribute("listaClubes", clubeService.listarTodos());

            return "clube/cadastro";

        } catch (EntityNotFoundException e){
            model.addAttribute("error", e.getMessage());
            return "redirect:/clube";
        }
    }

    @PostMapping("/salvar/{id}")
    public String salvarCuriosidade(@ModelAttribute("curiosidade") Curiosidade curiosidade,
                                    Model model){
        curiosidadeService.salvar(curiosidade);
        model.addAttribute("mensagem", "Curiosidade salva com sucesso!");
        return "redirect:/clube";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/cadastraTipo";
    }
}