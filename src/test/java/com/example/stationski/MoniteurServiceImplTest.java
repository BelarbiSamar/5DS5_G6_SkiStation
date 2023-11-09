package com.example.stationski;

import com.example.stationski.entities.Moniteur;
import com.example.stationski.repositories.MoniteurRepository;
import com.example.stationski.services.MoniteurServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
//@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ActiveProfiles("test")


public class MoniteurServiceImplTest {

    @InjectMocks
    private MoniteurServiceImpl moniteurService;

    @Mock
    private MoniteurRepository moniteurRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllMoniteurs() {
        // Créez une liste factice de moniteurs pour simuler la réponse de la base de données.
        List<Moniteur> moniteurs = new ArrayList<>();
        Moniteur moniteur1 = new Moniteur();
        moniteur1.setIdMoniteur(1);
        moniteur1.setNomM("Moniteur 1");
        moniteurs.add(moniteur1);

        Moniteur moniteur2 = new Moniteur();
        moniteur2.setIdMoniteur(2);
        moniteur2.setNomM("Moniteur 2");
        moniteurs.add(moniteur2);

        // Définissez le comportement attendu lorsque moniteurRepository.findAll() est appelé.
        Mockito.when(moniteurRepository.findAll()).thenReturn(moniteurs);

        // Appelez la méthode de service que vous voulez tester.
        List<Moniteur> retrievedMoniteurs = moniteurService.retrieveAllMoniteurs();

        // Vérifiez que la liste retournée par le service correspond à la liste factice que vous avez créée.
        assertEquals(moniteurs, retrievedMoniteurs);
    }

    @Test
    public void testAddMoniteur() {
        Moniteur moniteur = new Moniteur();
        moniteur.setIdMoniteur(1);
        moniteur.setNomM("Moniteur Test");
        moniteur.setDateRecru(LocalDate.now());

        Mockito.when(moniteurRepository.save(any(Moniteur.class))).thenReturn(moniteur);

        Moniteur addedMoniteur = moniteurService.addMoniteur(moniteur);

        assertNotNull(addedMoniteur);
        assertEquals(moniteur.getIdMoniteur(), addedMoniteur.getIdMoniteur());
        assertEquals(moniteur.getNomM(), addedMoniteur.getNomM());
        assertEquals(moniteur.getDateRecru(), addedMoniteur.getDateRecru());
    }

    @Test
    public void testUpdateMoniteur() {
        Moniteur moniteur = new Moniteur();
        moniteur.setIdMoniteur(1);
        moniteur.setNomM("Moniteur Test");
        moniteur.setDateRecru(LocalDate.now());

        Mockito.when(moniteurRepository.save(any(Moniteur.class))).thenReturn(moniteur);

        Moniteur updatedMoniteur = moniteurService.updateMoniteur(moniteur);

        assertNotNull(updatedMoniteur);
        assertEquals(moniteur.getIdMoniteur(), updatedMoniteur.getIdMoniteur());
        assertEquals(moniteur.getNomM(), updatedMoniteur.getNomM());
        assertEquals(moniteur.getDateRecru(), updatedMoniteur.getDateRecru());
    }




    @Test
    public void testAddMoniteurAndAssignToCourse() {
        Moniteur moniteur = new Moniteur();
        moniteur.setIdMoniteur(1);
        moniteur.setNomM("Moniteur Test");
        moniteur.setDateRecru(LocalDate.now());

        Mockito.when(moniteurRepository.save(any(Moniteur.class))).thenReturn(moniteur);

        Moniteur addedMoniteur = moniteurService.addMoniteurAndAssignToCourse(moniteur);

        assertNotNull(addedMoniteur);
        assertEquals(moniteur.getIdMoniteur(), addedMoniteur.getIdMoniteur());
        assertEquals(moniteur.getNomM(), addedMoniteur.getNomM());
        assertEquals(moniteur.getDateRecru(), addedMoniteur.getDateRecru());
    }
}
