package com.fatez.labbd.CampusQuest.service;

import com.fatez.labbd.CampusQuest.entity.Candidato;
import com.fatez.labbd.CampusQuest.entity.Clube;
import com.fatez.labbd.CampusQuest.repository.ClubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClubeService {

    @Autowired
    private ClubeRepository repository;

    public List<Clube> listarTodos(){
        return repository.findAll();
    }

    public Optional<Clube> buscarPorId(String id){
        return repository.findById(id);
    }

    public void salvar(Clube clube){
        repository.save(clube);
    }

    public void deletar(String id){
        repository.deleteById(id);
    }
}
