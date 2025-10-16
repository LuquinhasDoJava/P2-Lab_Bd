package com.fatez.labbd.CampusQuest.service;

import com.fatez.labbd.CampusQuest.entity.Candidato;
import com.fatez.labbd.CampusQuest.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository repository;

    public List<Candidato> listarTodos(){
        return repository.findAll();
    }

    public Optional<Candidato> buscarPorId(String id){
        return repository.findById(id);
    }

    public void salvar(Candidato candidato){
        repository.save(candidato);
    }

    public void deletar(String id){
        repository.deleteById(id);
    }
}
