package com.fatez.labbd.CampusQuest.service;

import com.fatez.labbd.CampusQuest.entity.Clube;
import com.fatez.labbd.CampusQuest.entity.Curiosidade;
import com.fatez.labbd.CampusQuest.repository.ClubeRepository;
import com.fatez.labbd.CampusQuest.repository.CuriosidadeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

@Service
public class CarregarBancoDeDados {

    @Autowired
    private ClubeRepository clubeRepository;

    @Autowired
    private CuriosidadeRepository curiosidadeRepository;

    @PostConstruct
    public void populaBancoDeDados(){
        if(!tabelaClubePopulada()){
            carregarClubes();
            carregarCuriosidades();
        }
    }

    private void carregarCuriosidades() {
        List<Clube> clubes = clubeRepository.findAll();

        for(Clube clube : clubes){
            String nomeArquivo = "classpath:txts/"+clube.getId().toLowerCase() + ".txt";
            carregarCuriosidadesPorTime(clube, nomeArquivo);
        }

    }

    private void carregarCuriosidadesPorTime(Clube clube, String nomeArquivo) {
        try{
            if(!tabelaClubePopulada()){
                File arquivo = ResourceUtils.getFile(nomeArquivo);
                List<String> linhas = Files.readAllLines(arquivo.toPath(), StandardCharsets.UTF_8);

                for(String linha : linhas){
                    if(!linha.trim().isEmpty()){
                        Curiosidade curiosidade = new Curiosidade(linha, clube);
                        curiosidadeRepository.save(curiosidade);
                    }
                }
            }
        } catch (Exception e ) {
           throw new RuntimeException("Erro ao carregar curiosidades por time: "+ e.getMessage());
        }
    }

    private void carregarClubes() {
        try {
            File arquivo = ResourceUtils.getFile("classpath:txts/Times.txt");
            List<String> linhas = Files.readAllLines(arquivo.toPath(), StandardCharsets.UTF_8);

            for(String linha : linhas){
                if(!linha.trim().isEmpty()){
                    String nome = linha;
                    Clube clube = new Clube(nome);
                    clubeRepository.save(clube);
                }
            }

        } catch (Exception e){
            System.err.println("Erro ao carregar Clubes no Banco de Dados: "+e.getMessage());
        }
    }

    public boolean tabelaClubePopulada(){
        try {
            long qtdLinhas = clubeRepository.count();
            if(qtdLinhas == 0){
                return false;
            }
            List<Clube> clubes = clubeRepository.findAll();
            for(Clube clube : clubes){
                long qtdCuriosidades = curiosidadeRepository.countByClubeId(clube.getId());
                if(qtdCuriosidades < 15){
                    return false;
                }
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
