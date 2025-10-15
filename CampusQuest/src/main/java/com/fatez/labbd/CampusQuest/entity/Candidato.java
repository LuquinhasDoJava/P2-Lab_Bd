package com.fatez.labbd.CampusQuest.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Candidato {

    @Id
    @Column(length = 11)
    private String celular;

    @ManyToOne
    @Column(name = "clube")
    @JoinColumn(name = "nome")
    private Clube clube;

    @Column(length = 100)
    private String nome;

    @Column(length = 64)
    private String email;

    @Column(length = 40)
    private String cursoInteresse;

    @Column(name = "registro")
    private Timestamp horarioRegistro;

    public Candidato(){}

    public Candidato(String celular){
        this.celular = celular;
    }

    public Candidato(String celular, String nome, String email, String cursoInteresse, Clube clube, Timestamp horarioRegistro) {
        this.celular = celular;
        this.nome = nome;
        this.email = email;
        this.cursoInteresse = cursoInteresse;
        this.clube = clube;
        this.horarioRegistro = horarioRegistro;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCursoInteresse() {
        return cursoInteresse;
    }

    public void setCursoInteresse(String cursoInteresse) {
        this.cursoInteresse = cursoInteresse;
    }

    public Clube getTime() {
        return clube;
    }

    public void setTime(Clube clube) {
        this.clube = clube;
    }

    public Timestamp getHorarioRegistro() {
        return horarioRegistro;
    }

    public void setHorarioRegistro(Timestamp horarioRegistro) {
        this.horarioRegistro = horarioRegistro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidato candidato = (Candidato) o;
        return celular != null ? celular.equals(candidato.celular) : candidato.celular == null;
    }

    @Override
    public int hashCode() {
        return celular != null ? celular.hashCode() : 0;
    }
}
