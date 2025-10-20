package com.fatez.labbd.CampusQuest.service;

import com.fatez.labbd.CampusQuest.entity.Clube;
import com.fatez.labbd.CampusQuest.repository.ClubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClubeService {

    @Autowired
    private ClubeRepository clubeRepository;

    public List<Clube> listarTodos(){
        return clubeRepository.findAll();
    }

    public Optional<Clube> buscarPorId(String id){
        return clubeRepository.findById(id);
    }

    public void salvar(Clube clube){
        clubeRepository.save(clube);
    }

    public void deletar(String id){
        clubeRepository.deleteById(id);
    }


    public List<Clube> buscarPorIdContendo(String termo) {
        return clubeRepository.findByIdContaining(termo);
    }

    public List<Clube> buscarOrdenadosPorId() {
        return clubeRepository.findAllByOrderByIdAsc();
    }

    public List<Clube> buscarClubesComCuriosidades() {
        return clubeRepository.findClubesComCuriosidades();
    }

    public List<Clube> buscarClubesSemCuriosidades() {
        return clubeRepository.findClubesSemCuriosidades();
    }

    public List<Object[]> contarCuriosidadesPorClube() {
        return clubeRepository.countCuriosidadesPorClube();
    }


    public List<Clube> buscarClubesComMaisCuriosidades(int minCuriosidades) {
        return clubeRepository.findClubesComMaisCuriosidades(minCuriosidades);
    }

    public List<Object[]> buscarEstatisticasClubes() {
        return clubeRepository.findEstatisticasClubes();
    }

    public List<Clube> buscarClubesPorPadraoId(String pattern) {
        return clubeRepository.findClubesPorPadraoId(pattern);
    }

    public boolean podeDeletarClube(String id) {
        Optional<Clube> clube = buscarPorId(id);
        return clube.isPresent() &&
                (clube.get().getCuriosidades() == null || clube.get().getCuriosidades().isEmpty());
    }
}