package com.fatez.labbd.CampusQuest.repository;

import com.fatez.labbd.CampusQuest.entity.Clube;
import com.fatez.labbd.CampusQuest.entity.Curiosidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuriosidadeRepository extends JpaRepository<Curiosidade, Integer> {

    List<Curiosidade> findByDescricao(String descricao);

    @Query("SELECT c FROM Curiosidade c WHERE c.clube = :clube")
    List<Curiosidade> findByClube(@Param("clube") Clube clube);

    @Query(value = "SELECT * FROM curiosidade WHERE clube_id = ?1", nativeQuery = true)
    List<Curiosidade> findByClubeIdNative(String clubeId);

    @Query(value = "EXEC sp_receberCuriosidade :clubeId", nativeQuery = true)
    Curiosidade getCuriosidadeAleatoria(@Param("clubeId") String clubeId);

    int countByClubeId(String clubeId);

    List<Curiosidade> findByClubeId(String clubeId);
}