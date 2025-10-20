package com.fatez.labbd.CampusQuest.repository;

import com.fatez.labbd.CampusQuest.entity.Clube;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubeRepository extends JpaRepository<Clube, String> {


    boolean existsById(String id);

    long count();

    List<Clube> findByIdContaining(String id);

    List<Clube> findAllByOrderByIdAsc();


    @Query("SELECT DISTINCT c FROM Clube c WHERE c.curiosidades IS NOT EMPTY")
    List<Clube> findClubesComCuriosidades();

    @Query("SELECT c FROM Clube c WHERE c.curiosidades IS EMPTY")
    List<Clube> findClubesSemCuriosidades();

    @Query("SELECT c.id, SIZE(c.curiosidades) FROM Clube c ORDER BY SIZE(c.curiosidades) DESC")
    List<Object[]> countCuriosidadesPorClube();


    @Query(value = "SELECT c.* FROM clube c " +
            "WHERE (SELECT COUNT(*) FROM curiosidade cur WHERE cur.clube_id = c.id) > :minCuriosidades",
            nativeQuery = true)
    List<Clube> findClubesComMaisCuriosidades(@Param("minCuriosidades") int minCuriosidades);

    @Query(value = "SELECT c.id, c.nome, " +
            "COUNT(cur.id) as total_curiosidades, " +
            "CASE WHEN COUNT(cur.id) = 0 THEN 'SEM CURIOSIDADES' " +
            "     WHEN COUNT(cur.id) <= 3 THEN 'POUCAS CURIOSIDADES' " +
            "     ELSE 'MUITAS CURIOSIDADES' END as status " +
            "FROM clube c " +
            "LEFT JOIN curiosidade cur ON c.id = cur.clube_id " +
            "GROUP BY c.id, c.nome " +
            "ORDER BY total_curiosidades DESC",
            nativeQuery = true)
    List<Object[]> findEstatisticasClubes();

    @Query(value = "SELECT * FROM clube WHERE id LIKE :pattern",
            nativeQuery = true)
    List<Clube> findClubesPorPadraoId(@Param("pattern") String pattern);
}