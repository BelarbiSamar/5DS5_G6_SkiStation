package tn.esprit.spring.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegistrationServicesImplSpringTest {

    @Autowired
    private IRegistrationRepository registrationRepository;
    @Autowired
    private ISkierRepository skierRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IRegistrationServices registrationService;
    @Autowired
    private IInstructorRepository instructorRepository;
    @Autowired
    private IInstructorServices instructorService;

    private Registration registration;
    private long countBefore;

    @BeforeEach
    public void setUp() {
        countBefore = registrationRepository.count();
        registration = new Registration();
    }

    @AfterEach
    public void tearDown() {
        if (registration != null && registration.getNumRegistration() != null && registration.getNumRegistration() > 0) {
            registrationRepository.deleteById(registration.getNumRegistration());
        }
    }

    @Test
    @Order(1)
    public void testAddRegistrationAndAssignToSkier() {
        Skier skier = new Skier();
        skier.setFirstName("Test");
        skierRepository.save(skier);
        registration.setNumWeek(1);

        registration = registrationService.addRegistrationAndAssignToSkier(registration, skier.getNumSkier());
        long countAfter = registrationRepository.count();

        assertEquals(countBefore + 1, countAfter);
        assertTrue(registration.getNumRegistration() > 0);

    }

    @Test
    @Order(2)
    public void testAssignRegistrationToCourse() {
        // Create a Course
        Course course = new Course();
        course.setLevel(1);
        course.setTimeSlot(1);
        course = courseRepository.save(course);

        // Create a Skier
        Skier skier = new Skier();
        skier.setFirstName("Test");
        skierRepository.save(skier);

        // Create a Registration
        registration.setNumWeek(1);
        registration = registrationService.addRegistrationAndAssignToSkier(registration, skier.getNumSkier());


        // Assign the registration to the course
        registration = registrationService.assignRegistrationToCourse(registration.getNumRegistration(), course.getNumCourse());

        // Verify the count and course assignment
        assertEquals(countBefore + 1, registrationRepository.count());
        assertEquals(registration.getCourse().getNumCourse(), course.getNumCourse());
    }

    @Test
    @Order(3)
    public void testAddRegistrationAndAssignToSkierAndCourse_SkierNotFound() {
        // Arrange
        Registration registration = new Registration();
        registration.setNumWeek(1);
        Long numSkieur = 1L;
        Long numCours = 1L;

        // Act
        Registration result = registrationService.addRegistrationAndAssignToSkierAndCourse(registration, numSkieur, numCours);

        // Assert
        assertNull(result);
    }

    @Test
    @Order(4)
    public void testAddRegistrationAndAssignToSkierAndCourse_CourseNotFound() {
        // Arrange
        Registration registration = new Registration();
        registration.setNumWeek(1);


        Skier skier = new Skier();
        skier.setDateOfBirth(LocalDate.of(2000, 1, 1));
        skierRepository.save(skier);


        // Act
        Registration result = registrationService.addRegistrationAndAssignToSkierAndCourse(registration, skier.getNumSkier(), 1L);

        // Assert
        assertNotNull(result);
    }

    @Test
    @Order(5)
    public void testAddRegistrationAndAssignToSkierAndCourse_RegistrationAlreadyExists() {
        // Arrange
        Registration registration = new Registration();
        registration.setNumWeek(1);


        Skier skier = new Skier();
        skier.setDateOfBirth(LocalDate.of(2000, 1, 1));
        skierRepository.save(skier);

        Course course = new Course();
        course.setTypeCourse(TypeCourse.INDIVIDUAL);
        courseRepository.save(course);

        // Act
        Registration result = registrationService.addRegistrationAndAssignToSkierAndCourse(registration, skier.getNumSkier(), course.getNumCourse());
        Registration repeatedResult = registrationService.addRegistrationAndAssignToSkierAndCourse(registration, skier.getNumSkier(), course.getNumCourse());
        // Assert
        assertNotNull(result);
        assertNull(repeatedResult);

    }

    @Test
    @Order(6)
    public void testAddRegistrationAndAssignToSkierAndCourse_IndividualCourse_SkierAnyAge() {
        // Arrange
        registration = new Registration();
        registration.setNumWeek(1);


        Skier skier = new Skier();
        skier.setDateOfBirth(LocalDate.of(1990, 1, 1));
        skierRepository.save(skier);

        Course course = new Course();
        course.setTypeCourse(TypeCourse.INDIVIDUAL);
        courseRepository.save(course);


        // Act
        Registration result = registrationService.addRegistrationAndAssignToSkierAndCourse(registration, skier.getNumSkier(), course.getNumCourse());

        // Assert
        assertNotNull(result);
    }

    @Test
    @Order(7)
    public void testNumWeeksCourseOfInstructorBySupport_NoResults() {
        // Arrange
        Long numInstructor = 1L;
        Support support = Support.SKI;
        Instructor instructor = new Instructor();
        instructor.setFirstName("Test");
        instructorRepository.save(instructor);

        // Act
        List<Integer> result = registrationService.numWeeksCourseOfInstructorBySupport(instructor.getNumInstructor(), support);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    @Order(8)
    public void testNumWeeksCourseOfInstructorBySupport_SingleResult() {
        // Arrange
        Support support = Support.SKI;
        Course course = new Course();
        course.setTypeCourse(TypeCourse.INDIVIDUAL);
        course.setSupport(support);
        course = courseRepository.save(course);

        Instructor instructor = new Instructor();
        instructor.setFirstName("Test");

        instructor = instructorService.addInstructorAndAssignToCourse(instructor, course.getNumCourse());


        registration.setNumWeek(1);
        registration = registrationRepository.save(registration);
        registrationService.assignRegistrationToCourse(registration.getNumRegistration(), course.getNumCourse());
        // Act
        List<Integer> result = registrationService.numWeeksCourseOfInstructorBySupport(instructor.getNumInstructor(), support);

        // Assert
        assertEquals(1, result.size());
        assertEquals(1, result.get(0));
    }


    @Test
    @Order(9)
    public void testNumWeeksCourseOfInstructorBySupport_MultipleResults() {
        // Arrange
        Support support = Support.SKI;
        Course course = new Course();
        course.setTypeCourse(TypeCourse.INDIVIDUAL);
        course.setSupport(support);
        course = courseRepository.save(course);

        Course course2 = new Course();
        course2.setTypeCourse(TypeCourse.INDIVIDUAL);
        course2.setSupport(support);
        course2 = courseRepository.save(course);

        Instructor instructor = new Instructor();
        instructor.setFirstName("Test");
        instructor = instructorService.addInstructorAndAssignToCourse(instructor, course.getNumCourse());


        registration.setNumWeek(1);
        registration = registrationRepository.save(registration);
        registrationService.assignRegistrationToCourse(registration.getNumRegistration(), course.getNumCourse());
        registrationService.assignRegistrationToCourse(registration.getNumRegistration(), course2.getNumCourse());
        // Act
        List<Integer> result = registrationService.numWeeksCourseOfInstructorBySupport(instructor.getNumInstructor(), support);

        // Assert
        assertFalse(result.size()>1);
    }
}





