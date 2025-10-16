package com.fatez.labbd.CampusQuest.service;

import com.fatez.labbd.CampusQuest.entity.Clube;
import com.fatez.labbd.CampusQuest.entity.Curiosidade;
import com.fatez.labbd.CampusQuest.repository.CuriosidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuriosidadeService {

    @Autowired
    private CuriosidadeRepository repository;

    public List<Curiosidade> listarTodos(){
        return  repository.findAll();
    }

    public Optional<Curiosidade> buscarPorId(int id){
        return repository.findById(id);
    }

    public void salvar(Curiosidade curiosidade){
        repository.save(curiosidade);
    }

    public void deletar(int id){
        repository.deleteById(id);
    }

    public List<Curiosidade> getCuriosidadePorDescricao(String descricao){
        return repository.findByDescricao(descricao);
    }

    public List<Curiosidade> getCuriosidadePorClube(Clube clube){
        return repository.findByClube(clube);
    }

    public List<Curiosidade> getCuriosidadePorClubeId(String clubeId){
        return repository.findByClubeIdNative(clubeId);
    }
}
