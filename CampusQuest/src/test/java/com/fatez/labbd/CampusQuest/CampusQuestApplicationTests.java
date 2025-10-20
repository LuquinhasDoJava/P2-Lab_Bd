package com.fatez.labbd.CampusQuest;

import com.fatez.labbd.CampusQuest.entity.Clube;
import com.fatez.labbd.CampusQuest.repository.ClubeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CampusQuestApplicationTests {

    @Autowired
    ClubeRepository clubeRepository;

	@Test
	void contextLoads() {
	}

}
