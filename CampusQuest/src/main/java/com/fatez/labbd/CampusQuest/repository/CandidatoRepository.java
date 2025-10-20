package com.fatez.labbd.CampusQuest.repository;

import com.fatez.labbd.CampusQuest.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, String> {

    List<Candidato> findByCursoInteresseContainingIgnoreCase(String curso);

    List<Candidato> findByBairroContainingIgnoreCase(String bairro);

    List<Candidato> findAllByOrderByCursoInteresseAsc();

    List<Candidato> findAllByOrderByBairroAsc();

    List<Candidato> findTop10ByOrderByHorarioRegistroAsc();

    List<Candidato> findTop10ByOrderByHorarioRegistroDesc();

    @Query("SELECT DISTINCT c.cursoInteresse FROM Candidato c ORDER BY c.cursoInteresse")
    List<String> findDistinctCursos();

    @Query("SELECT DISTINCT c.bairro FROM Candidato c WHERE c.bairro IS NOT NULL ORDER BY c.bairro")
    List<String> findDistinctBairros();

    @Query(value = "SELECT * FROM candidato c WHERE c.clube_id = :clubeId ORDER BY c.registro DESC", nativeQuery = true)
    List<Candidato> findByClubeIdNative(@Param("clubeId") String clubeId);

    @Query(value = "SELECT curso_interesse, COUNT(*) as total FROM candidato GROUP BY curso_interesse ORDER BY total DESC", nativeQuery = true)
    List<Object[]> countCandidatosByCursoNative();
}