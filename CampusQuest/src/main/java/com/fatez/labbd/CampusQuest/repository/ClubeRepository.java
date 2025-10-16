package com.fatez.labbd.CampusQuest.repository;

import com.fatez.labbd.CampusQuest.entity.Clube;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface ClubeRepository extends JpaRepository<Clube, String> {


}