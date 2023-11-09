package tn.esprit.spring;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TestCourse {
    @Mock
    private ICourseRepository courseRepository;
    @InjectMocks
    private CourseServicesImpl courseService;
    @Test
    void testRetrieveAllCourses() {
        // Given
        List<Course> mockCourses = new ArrayList<>();
        mockCourses.add(new Course());
        mockCourses.add(new Course());

        when(courseRepository.findAll()).thenReturn(mockCourses);

        // When
        List<Course> result = courseService.retrieveAllCourses();

        // Then
        assertNotNull(result);
        assertEquals(mockCourses.size(), result.size());
        verify(courseRepository).findAll();
    }
    @Test
    void testAddCourse() {
        // Given
        Course courseToAdd = new Course();
        when(courseRepository.save(any(Course.class))).thenReturn(courseToAdd);

        // When
        Course result = courseService.addCourse(courseToAdd);

        // Then
        assertNotNull(result);
        verify(courseRepository, times(1)).save(any(Course.class));
    }
    @Test
    void testUpdateCourse() {
        // Given
        Long courseId = 1L;
        Course courseToUpdate = new Course();
        courseToUpdate.setId(courseId);

        when(courseRepository.save(any(Course.class))).thenReturn(courseToUpdate);

        // When
        Course result = courseService.updateCourse(courseToUpdate);

        // Then
        assertNotNull(result);
        verify(courseRepository, times(1)).save(any(Course.class));
    }
    @Test
    void testRetrieveCourse() {
        // Given
        Long courseId = 1L;
        Course expectedCourse = new Course();
        expectedCourse.setId(courseId);

        
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(expectedCourse));

        // When
        Course result = courseService.retrieveCourse(courseId);

        // Then
        assertNotNull(result);
        assertEquals(courseId, result.getId());
        verify(courseRepository).findById(courseId);
    }




}
