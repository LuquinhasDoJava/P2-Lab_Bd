package com.fatez.labbd.CampusQuest.controller;

import com.fatez.labbd.CampusQuest.entity.Curiosidade;
import com.fatez.labbd.CampusQuest.service.ClubeService;
import com.fatez.labbd.CampusQuest.service.CuriosidadeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/curiosidade")
public class CuriosidadeController {

    @Autowired
    private ClubeService clubeService;

    @Autowired
    private CuriosidadeService curiosidadeService;

    @GetMapping
    public String acessarPagina(HttpSession session, Model model,
                                @RequestParam(required = false) String descricao,
                                @RequestParam(required = false) String clubeId) {
        // Verificar autenticação
        if (session.getAttribute("usuarioAutenticado") == null) {
            return "redirect:/login";
        }

        List<Curiosidade> curiosidadesExibicao;

        if (descricao != null && !descricao.trim().isEmpty()) {
            curiosidadesExibicao = curiosidadeService.getCuriosidadePorDescricao(descricao);
        } else if (clubeId != null && !clubeId.trim().isEmpty()) {
            curiosidadesExibicao = curiosidadeService.getCuriosidadePorClubeId(clubeId);
        } else {
            curiosidadesExibicao = curiosidadeService.listarTodos();
        }

        // ADICIONAR ESTA LINHA - Inicializar o objeto curiosidade
        model.addAttribute("curiosidade", new Curiosidade());
        model.addAttribute("listaCuriosidadeExibicao", curiosidadesExibicao);
        model.addAttribute("listaClubes", clubeService.listarTodos());
        return "curiosidade/cadastro";
    }

    @GetMapping("/pesquisar")
    public String pesquisarCuriosidades(HttpSession session, Model model,
                                        @RequestParam(required = false) String descricao,
                                        @RequestParam(required = false) String clubeId) {
        return acessarPagina(session, model, descricao, clubeId);
    }

    // Salvar nova curiosidade
    @PostMapping("/salvar")
    public String salvarCuriosidade(@ModelAttribute Curiosidade curiosidade,
                                    HttpSession session,
                                    Model model) {
        // Verificar autenticação
        if (session.getAttribute("usuarioAutenticado") == null) {
            return "redirect:/login";
        }

        try {
            curiosidadeService.salvar(curiosidade);
            model.addAttribute("mensagem", "Curiosidade salva com sucesso!");
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar curiosidade: " + e.getMessage());
        }
        return "redirect:/curiosidade";
    }

    // Atualizar curiosidade existente
    @PostMapping("/salvar/{id}")
    public String atualizarCuriosidade(@PathVariable int id,
                                       @ModelAttribute Curiosidade curiosidade,
                                       HttpSession session,
                                       Model model) {
        // Verificar autenticação
        if (session.getAttribute("usuarioAutenticado") == null) {
            return "redirect:/login";
        }

        try {
            curiosidade.setId(id);
            curiosidadeService.salvar(curiosidade);
            model.addAttribute("mensagem", "Curiosidade atualizada com sucesso!");
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao atualizar curiosidade: " + e.getMessage());
        }
        return "redirect:/curiosidade";
    }

    @PostMapping("/excluir/{id}")
    public String excluirCuriosidade(@PathVariable int id,
                                     HttpSession session,
                                     Model model) {
        // Verificar autenticação
        if (session.getAttribute("usuarioAutenticado") == null) {
            return "redirect:/login";
        }

        try {
            curiosidadeService.deletar(id);
            model.addAttribute("mensagem", "Curiosidade excluída com sucesso!");
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao excluir curiosidade: " + e.getMessage());
        }
        return "redirect:/curiosidade";
    }

    @GetMapping("/cancelar")
    public String cancelarEdicao(HttpSession session) {
        if (session.getAttribute("usuarioAutenticado") == null) {
            return "redirect:/login";
        }
        return "redirect:/curiosidade";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}