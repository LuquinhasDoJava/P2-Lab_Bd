package com.fatez.labbd.CampusQuest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;

@Entity
public class Clube {

    @Id
    @Column(length = 11)
    private String nome;

    @OneToMany(mappedBy = "clube")
    private ArrayList<Curiosidade> curiosidades;

    public Clube(){}

    public Clube(String nome){
        this.nome = nome;
    }

    public Clube(String nome, ArrayList<Curiosidade> curiosidades) {
        this.nome = nome;
        this.curiosidades = curiosidades;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Curiosidade> getCuriosidades() {
        return curiosidades;
    }

    public void setCuriosidades(ArrayList<Curiosidade> curiosidades) {
        this.curiosidades = curiosidades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clube clube = (Clube) o;
        return nome != null ? nome.equals(clube.nome) : clube.nome == null;
    }

    @Override
    public int hashCode() {
        return nome != null ? nome.hashCode() : 0;
    }
}
