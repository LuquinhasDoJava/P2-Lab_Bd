package com.fatez.labbd.CampusQuest.controller;

import com.fatez.labbd.CampusQuest.entity.Candidato;
import com.fatez.labbd.CampusQuest.service.CandidatoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/consultaCandidatos")
public class ConsultaCandidatosController {

    @Autowired
    private CandidatoService candidatoService;

    @GetMapping
    public String listarCandidatos(HttpSession session, Model model,
                                   @RequestParam(required = false) String curso,
                                   @RequestParam(required = false) String bairro,
                                   @RequestParam(required = false) String ordenarPor,
                                   @RequestParam(required = false) String tipoConsulta) {

        if (session.getAttribute("usuarioAutenticado") == null) {
            return "login";
        }

        List<Candidato> candidatos;
        String titulo = "Todos os Candidatos";

        try {
            if (tipoConsulta != null) {
                switch (tipoConsulta) {
                    case "primeiros10":
                        candidatos = candidatoService.findTop10ByOrderByHorarioRegistroAsc();
                        titulo = "10 Primeiros Cadastrados";
                        break;
                    case "ultimos10":
                        candidatos = candidatoService.findTop10ByOrderByHorarioRegistroDesc();
                        titulo = "10 Últimos Cadastrados";
                        break;
                    default:
                        candidatos = candidatoService.findAll();
                }
            }
            else if (curso != null && !curso.trim().isEmpty()) {
                candidatos = candidatoService.findByCursoInteresse(curso);
                titulo = "Candidatos do Curso: " + curso;
            }
            else if (bairro != null && !bairro.trim().isEmpty()) {
                candidatos = candidatoService.findByBairro(bairro);
                titulo = "Candidatos do Bairro: " + bairro;
            }
            else if (ordenarPor != null) {
                switch (ordenarPor) {
                    case "curso":
                        candidatos = candidatoService.findAllOrderByCursoInteresse();
                        titulo = "Candidatos Ordenados por Curso";
                        break;
                    case "bairro":
                        candidatos = candidatoService.findAllOrderByBairro();
                        titulo = "Candidatos Ordenados por Bairro";
                        break;
                    default:
                        candidatos = candidatoService.findAll();
                }
            }
            else {
                candidatos = candidatoService.findAll();
                titulo = "Todos os Candidatos";
            }

            model.addAttribute("candidatos", candidatos);
            model.addAttribute("titulo", titulo);
            model.addAttribute("totalGeral", candidatoService.countTotalCandidatos());
            model.addAttribute("cursosDisponiveis", candidatoService.findDistinctCursos());
            model.addAttribute("bairrosDisponiveis", candidatoService.findDistinctBairros());
            model.addAttribute("totalCandidatos", candidatos.size());
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar candidatos: " + e.getMessage());
            model.addAttribute("candidatos", List.of());
            model.addAttribute("totalCandidatos", 0);
            model.addAttribute("totalGeral", 0);
            model.addAttribute("cursosDisponiveis", List.of());
            model.addAttribute("bairrosDisponiveis", List.of());
        }

        return "consultaCandidatos";
    }

    @GetMapping("/curso")
    public String consultarPorCurso(@RequestParam String curso, HttpSession session, Model model) {
        return listarCandidatos(session, model, curso, null, null, null);
    }

    @GetMapping("/bairro")
    public String consultarPorBairro(@RequestParam String bairro, HttpSession session, Model model) {
        return listarCandidatos(session, model, null, bairro, null, null);
    }

    @GetMapping("/ordenar/curso")
    public String ordenarPorCurso(HttpSession session, Model model) {
        return listarCandidatos(session, model, null, null, "curso", null);
    }

    @GetMapping("/ordenar/bairro")
    public String ordenarPorBairro(HttpSession session, Model model) {
        return listarCandidatos(session, model, null, null, "bairro", null);
    }

    @GetMapping("/primeiros10")
    public String primeiros10Cadastrados(HttpSession session, Model model) {
        return listarCandidatos(session, model, null, null, null, "primeiros10");
    }

    @GetMapping("/ultimos10")
    public String ultimos10Cadastrados(HttpSession session, Model model) {
        return listarCandidatos(session, model, null, null, null, "ultimos10");
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/cadastraTipo";
    }
}