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

import java.util.List;

@Controller
@RequestMapping("/curiosidade")
public class CuriosidadeController {

    @Autowired
    private ClubeService clubeService;

    @Autowired
    private CuriosidadeService curiosidadeService;

    @GetMapping
    public String acessarPagina(HttpSession session, Model model, @RequestParam(required = false) String descricao, @RequestParam(required = false) String clubeId) {
        if (session.getAttribute("usuarioAutenticado") == null ||
                !Boolean.TRUE.equals(session.getAttribute("usuarioAutenticado"))) {
            return "redirect:/cadastraTipo";
        }
        List<Curiosidade> curiosidadesExibicao;

        if (descricao != null && !descricao.trim().isEmpty()) {
            curiosidadesExibicao = curiosidadeService.getCuriosidadePorDescricao(descricao);
            model.addAttribute("listaCuriosidadeFiltrada", curiosidadesExibicao);
        } else if (clubeId != null && !clubeId.trim().isEmpty()) {
            curiosidadesExibicao = curiosidadeService.getCuriosidadePorClubeId(clubeId);
            model.addAttribute("listaCuriosidadeFiltrada", curiosidadesExibicao);
        } else {
            curiosidadesExibicao = curiosidadeService.listarTodos();
        }

        model.addAttribute("listaCuriosidadeExibicao", curiosidadesExibicao);
        model.addAttribute("listaCuriosidade", curiosidadeService.listarTodos());

        if (!model.containsAttribute("curiosidade")) {
            model.addAttribute("curiosidade", new Curiosidade());
        }

        model.addAttribute("listaClubes", clubeService.listarTodos());

        return "curiosidade/cadastro";
    }

    @GetMapping("/pesquisar")
    public String pesquisarCuriosidades(@RequestParam(required = false) String descricao, @RequestParam(required = false) String clubeId, HttpSession session, Model model) {
        return acessarPagina(session, model, descricao, clubeId);
    }

    @GetMapping("/editar/{id}")
    public String editarCuriosidade(@PathVariable int id, Model model, HttpSession session){
        if (session.getAttribute("usuarioAutenticado") == null ||
                !Boolean.TRUE.equals(session.getAttribute("usuarioAutenticado"))) {
            return "redirect:/cadastraTipo";
        }

        try{
            Curiosidade curiosidade = curiosidadeService.buscarPorId(id).orElseThrow(() -> new EntityNotFoundException("Curiosidade não encontrada!"));

            model.addAttribute("curiosidade", curiosidade);
            model.addAttribute("listaCuriosidade", curiosidadeService.listarTodos());
            model.addAttribute("listaClubes", clubeService.listarTodos());

            return "curiosidade/cadastro";

        } catch (EntityNotFoundException e){
            model.addAttribute("error", e.getMessage());
            return "redirect:/curiosidade";
        }
    }

    @PostMapping("/salvar/{id}")
    public String salvarCuriosidade(@ModelAttribute("curiosidade") Curiosidade curiosidade, Model model){
        curiosidadeService.salvar(curiosidade);
        model.addAttribute("mensagem", "Curiosidade salva com sucesso!");
        return "redirect:/clube";
    }

    @PostMapping("/excluir/{id}")
    public String excluirCuriosidade(@PathVariable int id, Model model){
        try {
            curiosidadeService.deletar(id);
            model.addAttribute("mensagem", "Curiosidade excluída com sucesso!");
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao excluir curiosidade: " + e.getMessage());
        }
        return "redirect:/curiosidade";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/cadastraTipo";
    }
}