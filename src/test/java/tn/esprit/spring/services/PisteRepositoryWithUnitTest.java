package tn.esprit.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;

import java.util.List;

import static org.junit.Assert.assertNotNull;
//mvn test -Dtest=PisteRepositoryTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class PisteRepositoryWithUnitTest {

	@Autowired
	private IPisteRepository pisteRepository;
	Piste piste  = Piste.builder().numPiste(14L).namePiste("Piste1").color(Color.RED).length(4).slope(500).build();



	@Test
	@Order(0)
	public void ajouterPisteTest() {
		piste = pisteRepository.save(piste);
		log.info(piste.toString());
		Assertions.assertNotNull(piste.getNumPiste());
	}

	@Test
	@Order(1)
	public void modifierPisteTest() {
		piste.setNamePiste("Piste2");
		piste = pisteRepository.save(piste);
		log.info(piste.toString());
		Assertions.assertNotEquals(piste.getNamePiste(), "Piste3");
	}

	@Test
	@Order(2)
	public void listerPistes() {
		List<Piste> list = pisteRepository.findAll();
		log.info(list.size()+"");
		Assertions.assertTrue(list.size() > 0);
	}



	@Test
	@Order(4)
	public void supprimerPiste() {
		pisteRepository.delete(piste);
	}


}