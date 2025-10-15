package com.fatez.labbd.CampusQuest.entity;

import com.fatez.labbd.CampusQuest.serializable.CuriosidadeId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class Curiosidade {

    @EmbeddedId
    private CuriosidadeId curiosidadeId;

    @ManyToOne
    private Clube clube;

    private String descricao;

    public Curiosidade(){}

    public Curiosidade(CuriosidadeId curiosidadeId){
        this.curiosidadeId = curiosidadeId;
    }

    public Curiosidade(CuriosidadeId curiosidadeId, Clube clube, String descricao) {
        this.curiosidadeId = curiosidadeId;
        this.clube = clube;
        this.descricao = descricao;
    }

    public CuriosidadeId getCuriosidadeId() {
        return curiosidadeId;
    }

    public void setCuriosidadeId(CuriosidadeId curiosidadeId) {
        this.curiosidadeId = curiosidadeId;
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
        return Objects.equals(curiosidadeId, that.curiosidadeId) && Objects.equals(clube, that.clube) && Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(curiosidadeId, clube, descricao);
    }
}
