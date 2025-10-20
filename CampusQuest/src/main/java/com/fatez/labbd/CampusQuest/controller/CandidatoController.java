package com.fatez.labbd.CampusQuest.controller;

import com.fatez.labbd.CampusQuest.entity.Candidato;
import com.fatez.labbd.CampusQuest.entity.Clube;
import com.fatez.labbd.CampusQuest.entity.Curiosidade;
import com.fatez.labbd.CampusQuest.service.CandidatoService;
import com.fatez.labbd.CampusQuest.service.ClubeService;
import com.fatez.labbd.CampusQuest.service.CuriosidadeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/candidato")
public class CandidatoController {

    @Autowired
    private CuriosidadeService curiosidadeService;
    @Autowired
    private ClubeService clubeService;
    @Autowired
    private CandidatoService candidatoService;

    @GetMapping("/escolher-time")
    public String escolherTime(Model model){
        model.addAttribute("listaClubes", clubeService.listarTodos());
        return "candidato/escolher-time";
    }

    @PostMapping("/mostrar-curiosidade")
    public String mostrarCuriosidade(@RequestParam String clubeId, Model model, HttpSession session){
        try{
            session.setAttribute("clubeEscolhidoId", clubeId);
            Curiosidade curiosidade = curiosidadeService.getCuriosidadeAleatoria(clubeId);
            var clube = clubeService.buscarPorId(clubeId).orElse(null);

            model.addAttribute("curiosidade", curiosidade);
            model.addAttribute("clube", clube);
            model.addAttribute("tempoExibicao", 15);
            return "candidato/curiosidade-exibicao";
        } catch (Exception e){
            model.addAttribute("erro", "Erro ao buscar curiosidade:"+e.getMessage());
            return "redirect:/candidato/escolher-time";
        }
    }

    @GetMapping("/cadastro")
    public String paginaCadastro(Model model, HttpSession session){
        String clubeId = (String) session.getAttribute("clubeEscolhidoId");
        if (clubeId == null) return "redirect:/candidato/escolher-time";

        Optional<Clube> clubeOpt = clubeService.buscarPorId(clubeId);
        if (clubeOpt.isPresent()) {
            model.addAttribute("clubeEscolhido", clubeOpt.get());
        } else {
            return "redirect:/candidato/escolher-time";
        }

        model.addAttribute("candidato", new Candidato());
        return "candidato/cadastro";
    }

    @PostMapping("/salvar")
    public String salvarCandidato(@ModelAttribute Candidato candidato,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        try {
            String clubeId = (String) session.getAttribute("clubeEscolhidoId");
            Optional<Clube> clubeOpt = clubeService.buscarPorId(clubeId);

            if (clubeOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Time não encontrado.");
                return "redirect:/candidato/escolher-time";
            }

            if (candidatoService.existePorCelular(candidato.getCelular())) {
                redirectAttributes.addFlashAttribute("error", "Este número de celular já está cadastrado.");
                redirectAttributes.addFlashAttribute("candidato", candidato);
                return "redirect:/candidato/cadastro";
            }

            candidato.setClube(clubeOpt.get());
            candidato.setHorarioRegistro(LocalDateTime.now());
            candidatoService.salvar(candidato);

            session.removeAttribute("clubeEscolhidoId");
            redirectAttributes.addFlashAttribute("success", "Cadastro realizado com sucesso!");
            return "redirect:/candidato/sucesso";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro: " + e.getMessage());
            return "redirect:/candidato/cadastro";
        }
    }

    @GetMapping("/sucesso")
    public String paginaSucesso() {
        return "candidato/sucesso";
    }
}