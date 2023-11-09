package tn.esprit.spring.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RegistrationServicesImplTest{

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ICourseRepository courseRepository;
    @Mock
    private ISkierRepository skierRepository;

    @InjectMocks
    private RegistrationServicesImpl registrationService;

    @Test
    public void testAddRegistrationAndAssignToSkier() {
        Registration registration = new Registration();

        Skier skier = new Skier();
        when(skierRepository.findById(1L)).thenReturn(java.util.Optional.of(skier));

        when(registrationRepository.save(Mockito.any(Registration.class))).thenReturn(registration);

        Registration result = registrationService.addRegistrationAndAssignToSkier(registration, 1L);

        verify(skierRepository).findById(1L);
        verify(registrationRepository).save(registration);

        assertEquals(skier, registration.getSkier());
        assertEquals(registration, result);
    }


}
