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
@RequestMapping("/clube")
public class ClubeController {

    @Autowired
    private ClubeService clubeService;

    @Autowired
    private CuriosidadeService curiosidadeService;

    @GetMapping
    public String acessarPagina(HttpSession session, Model model){
        if (session.getAttribute("usuarioAutenticado") == null || !Boolean.TRUE.equals(session.getAttribute("usuarioAutenticado"))) {
            return "login";
        }

        model.addAttribute("listaCuriosidade", curiosidadeService.listarTodos());
        model.addAttribute("listaCuriosidadeExibicao", curiosidadeService.listarTodos());

        if (!model.containsAttribute("curiosidade")) {
            model.addAttribute("curiosidade", new Curiosidade());
        }
        model.addAttribute("listaClubes", clubeService.listarTodos());
        model.addAttribute("titulo", "Gerenciar Curiosidades");

        return "clube/cadastro";
    }

    @GetMapping("/editar/{id}")
    public String editarCuriosidade(@PathVariable("id") int id, Model model, HttpSession session){
        if (session.getAttribute("usuarioAutenticado") == null ||
                !Boolean.TRUE.equals(session.getAttribute("usuarioAutenticado"))) {
            return "login";
        }

        try{
            Curiosidade curiosidade = curiosidadeService.buscarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Curiosidade não encontrada!"));

            model.addAttribute("curiosidade", curiosidade);
            model.addAttribute("listaCuriosidade", curiosidadeService.listarTodos());
            model.addAttribute("listaCuriosidadeExibicao", curiosidadeService.listarTodos());
            model.addAttribute("listaClubes", clubeService.listarTodos());
            model.addAttribute("modoEdicao", true);
            model.addAttribute("titulo", "Editar Curiosidade");

            return "clube/cadastro";

        } catch (EntityNotFoundException e){
            model.addAttribute("error", e.getMessage());
            return "redirect:/clube";
        }
    }

    @PostMapping("/salvar")
    public String salvarCuriosidade(@ModelAttribute("curiosidade") Curiosidade curiosidade,
                                    Model model, HttpSession session){
        if (session.getAttribute("usuarioAutenticado") == null ||
                !Boolean.TRUE.equals(session.getAttribute("usuarioAutenticado"))) {
            return "login";
        }

        try {
            curiosidadeService.salvar(curiosidade);
            model.addAttribute("mensagem", "Curiosidade salva com sucesso!");
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar curiosidade: " + e.getMessage());
        }

        return "redirect:/clube";
    }

    @PostMapping("/excluir/{id}")
    public String excluirCuriosidade(@PathVariable("id") int id, HttpSession session) {
        if (session.getAttribute("usuarioAutenticado") == null ||
                !Boolean.TRUE.equals(session.getAttribute("usuarioAutenticado"))) {
            return "login";
        }

        curiosidadeService.deletar(id);
        return "redirect:/clube";
    }

    @GetMapping("/buscar")
    public String buscarClubesPorId(@RequestParam("termo") String termo, HttpSession session, Model model) {
        if (session.getAttribute("usuarioAutenticado") == null ||
                !Boolean.TRUE.equals(session.getAttribute("usuarioAutenticado"))) {
            return "login";
        }

        try {
            var clubes = clubeService.buscarPorIdContendo(termo);
            model.addAttribute("listaClubes", clubes);
            model.addAttribute("listaCuriosidade", curiosidadeService.listarTodos());
            model.addAttribute("listaCuriosidadeExibicao", curiosidadeService.listarTodos());
            model.addAttribute("curiosidade", new Curiosidade());
            model.addAttribute("titulo", "Gerenciar Curiosidades");
            model.addAttribute("mensagem", "Clubes encontrados com: '" + termo + "' (Derived Query)");

        } catch (Exception e) {
            model.addAttribute("error", "Erro na busca: " + e.getMessage());
            model.addAttribute("listaClubes", clubeService.listarTodos());
        }

        return "clube/cadastro";
    }

    @GetMapping("/com-curiosidades")
    public String clubesComCuriosidades(HttpSession session, Model model) {
        if (session.getAttribute("usuarioAutenticado") == null ||
                !Boolean.TRUE.equals(session.getAttribute("usuarioAutenticado"))) {
            return "login";
        }

        try {
            // JPQL: findClubesComCuriosidades
            var clubes = clubeService.buscarClubesComCuriosidades();
            model.addAttribute("listaClubes", clubes);
            model.addAttribute("listaCuriosidade", curiosidadeService.listarTodos());
            model.addAttribute("listaCuriosidadeExibicao", curiosidadeService.listarTodos());
            model.addAttribute("curiosidade", new Curiosidade());
            model.addAttribute("titulo", "Gerenciar Curiosidades");
            model.addAttribute("mensagem", "Clubes que possuem curiosidades (JPQL)");

        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar clubes com curiosidades: " + e.getMessage());
            model.addAttribute("listaClubes", clubeService.listarTodos());
        }

        return "clube/cadastro";
    }

    @GetMapping("/sem-curiosidades")
    public String clubesSemCuriosidades(HttpSession session, Model model) {
        if (session.getAttribute("usuarioAutenticado") == null ||
                !Boolean.TRUE.equals(session.getAttribute("usuarioAutenticado"))) {
            return "login";
        }

        try {
            // JPQL: findClubesSemCuriosidades
            var clubes = clubeService.buscarClubesSemCuriosidades();
            model.addAttribute("listaClubes", clubes);
            model.addAttribute("listaCuriosidade", curiosidadeService.listarTodos());
            model.addAttribute("listaCuriosidadeExibicao", curiosidadeService.listarTodos());
            model.addAttribute("curiosidade", new Curiosidade());
            model.addAttribute("titulo", "Gerenciar Curiosidades");
            model.addAttribute("mensagem", "Clubes sem curiosidades (JPQL)");

        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar clubes sem curiosidades: " + e.getMessage());
            model.addAttribute("listaClubes", clubeService.listarTodos());
        }

        return "clube/cadastro";
    }

    @GetMapping("/estatisticas")
    public String estatisticasClubes(HttpSession session, Model model) {
        if (session.getAttribute("usuarioAutenticado") == null ||
                !Boolean.TRUE.equals(session.getAttribute("usuarioAutenticado"))) {
            return "login";
        }

        try {

            var estatisticas = clubeService.buscarEstatisticasClubes();
            model.addAttribute("estatisticas", estatisticas);
            model.addAttribute("titulo", "Estatísticas de Clubes (Native Query)");

        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar estatísticas: " + e.getMessage());
        }

        return "clube/estatisticas";
    }

    @GetMapping("/ranking")
    public String rankingClubes(HttpSession session, Model model) {
        if (session.getAttribute("usuarioAutenticado") == null ||
                !Boolean.TRUE.equals(session.getAttribute("usuarioAutenticado"))) {
            return "login";
        }

        try {
            var estatisticas = clubeService.buscarEstatisticasClubes();
            model.addAttribute("estatisticas", estatisticas);
            model.addAttribute("titulo", "Ranking de Clubes (Native Query)");

        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar ranking: " + e.getMessage());
        }

        return "clube/estatisticas";
    }

    @GetMapping("/curiosidades-por-clube")
    public String curiosidadesPorClube(HttpSession session, Model model) {
        if (session.getAttribute("usuarioAutenticado") == null ||
                !Boolean.TRUE.equals(session.getAttribute("usuarioAutenticado"))) {
            return "login";
        }

        try {
            var estatisticas = clubeService.contarCuriosidadesPorClube();
            model.addAttribute("estatisticas", estatisticas);
            model.addAttribute("titulo", "Curiosidades por Clube (JPQL)");

        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar estatísticas: " + e.getMessage());
        }

        return "clube/estatisticas";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @GetMapping("/cancelar")
    public String cancelarEdicao(HttpSession session) {
        if (session.getAttribute("usuarioAutenticado") == null ||
                !Boolean.TRUE.equals(session.getAttribute("usuarioAutenticado"))) {
            return "login";
        }
        return "redirect:/clube";
    }
}