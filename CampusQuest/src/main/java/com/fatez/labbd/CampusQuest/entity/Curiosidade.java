package com.fatez.labbd.CampusQuest.entity;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Curiosidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clube_id", nullable = false)
    private Clube clube;

    @Column(length = 255, nullable = false)
    private String descricao;

    public Curiosidade(){}

    public Curiosidade(int id){
        this.id = id;
    }

    public Curiosidade(int id, Clube clube, String descricao) {
        this.id = id;
        this.clube = clube;
        this.descricao = descricao;
    }

    public Curiosidade(String descricao, Clube clube) {
        this.descricao = descricao;
        this.clube = clube;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Clube getClube() {
        return clube;
    }

    public void setClube(Clube clube) {
        this.clube = clube;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Curiosidade that = (Curiosidade) o;
        return Objects.equals(id, that.id) && Objects.equals(clube, that.clube) && Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clube, descricao);
    }
}
