package com.fatez.labbd.CampusQuest.service;

import com.fatez.labbd.CampusQuest.entity.Candidato;
import com.fatez.labbd.CampusQuest.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    public List<Candidato> findByCursoInteresse(String curso) {
        return candidatoRepository.findByCursoInteresseContainingIgnoreCase(curso);
    }

    public List<Candidato> findByBairro(String bairro) {
        return candidatoRepository.findByBairroContainingIgnoreCase(bairro);
    }

    public List<Candidato> findAllOrderByCursoInteresse() {
        return candidatoRepository.findAllByOrderByCursoInteresseAsc();
    }

    public List<Candidato> findAllOrderByBairro() {
        return candidatoRepository.findAllByOrderByBairroAsc();
    }

    public List<Candidato> findTop10ByOrderByHorarioRegistroAsc() {
        return candidatoRepository.findTop10ByOrderByHorarioRegistroAsc();
    }

    public List<Candidato> findTop10ByOrderByHorarioRegistroDesc() {
        return candidatoRepository.findTop10ByOrderByHorarioRegistroDesc();
    }

    public List<String> findDistinctCursos() {
        return candidatoRepository.findDistinctCursos();
    }

    public List<String> findDistinctBairros() {
        return candidatoRepository.findDistinctBairros();
    }

    public long countTotalCandidatos() {
        return candidatoRepository.count();
    }

    public boolean existePorCelular(String celular) {
        return candidatoRepository.existsById(celular);
    }

    public void salvar(Candidato candidato) {
        candidatoRepository.save(candidato);
    }

    public List<Candidato> findAll() {
        return candidatoRepository.findAll();
    }
}