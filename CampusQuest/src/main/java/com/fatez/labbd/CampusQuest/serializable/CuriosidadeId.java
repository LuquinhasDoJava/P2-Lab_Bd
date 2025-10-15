package com.fatez.labbd.CampusQuest.serializable;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CuriosidadeId implements Serializable {

    private int id;
    private String nomeClube;

    public CuriosidadeId(){}

    public CuriosidadeId(int id, String nomeClube) {
        this.id = id;
        this.nomeClube = nomeClube;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeClube() {
        return nomeClube;
    }

    public void setNomeClube(String nomeClube) {
        this.nomeClube = nomeClube;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuriosidadeId curiosidade = (CuriosidadeId) o;
        return id == curiosidade.id && nomeClube.equals(curiosidade.nomeClube);
    }

    @Override
    public int hashCode() {
        int resultado = 17;
        resultado = 31 * resultado + id;
        resultado = 31 * resultado + nomeClube.hashCode();
        return resultado;
    }

}
