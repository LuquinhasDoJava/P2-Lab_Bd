package com.fatez.labbd.CampusQuest.repository;

import com.fatez.labbd.CampusQuest.entity.Clube;
import com.fatez.labbd.CampusQuest.entity.Curiosidade;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Transactional
public interface CuriosidadeRepository extends JpaRepository<Curiosidade, Integer> {

    List<Curiosidade> findByDescricao(String descricao);

    @Query("SELECT c FROM Curiosidade c WHERE c.clube = :clube")
    List<Curiosidade> findByClube(@Param("clube") Clube clube);

    @Query(value = "SELECT * FROM curiosidade WHERE clube_id LIKE ?1", nativeQuery = true)
    List<Curiosidade> findByClubeIdNative(String clubeId);
}