package com.fatez.labbd.CampusQuest.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Clube {

    @Id
    @Column(length = 11)
    private String id;

    @OneToMany(mappedBy = "clube", fetch = FetchType.LAZY)
    private List<Curiosidade> curiosidades;

    public Clube(){}

    public Clube(String id){
        this.id = id;
    }

    public Clube(String id, List<Curiosidade> curiosidades) {
        this.id = id;
        this.curiosidades = curiosidades;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Curiosidade> getCuriosidades() {
        return curiosidades;
    }

    public void setCuriosidades(List<Curiosidade> curiosidades) {
        this.curiosidades = curiosidades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clube clube = (Clube) o;
        return id != null ? id.equals(clube.id) : clube.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}