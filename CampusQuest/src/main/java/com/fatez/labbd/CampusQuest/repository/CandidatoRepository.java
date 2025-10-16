package com.fatez.labbd.CampusQuest.repository;

import com.fatez.labbd.CampusQuest.entity.Candidato;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface CandidatoRepository extends JpaRepository<Candidato, String> {


}