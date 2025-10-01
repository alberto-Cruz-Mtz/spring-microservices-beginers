package com.microservice.course.service;

import com.microservice.course.client.StudentClient;
import com.microservice.course.controller.sto.StudentDTO;
import com.microservice.course.entity.Course;
import com.microservice.course.http.response.StudentByCouseResponse;
import com.microservice.course.persistence.ICourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements ICourseService {

    private final ICourseRepository courseRepository;

    private final StudentClient studentClient;

    @Override
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    public StudentByCouseResponse findStudentsByCourseId(Long courseId) {

        // Consultar si existe el curso
        Course course = courseRepository.findById(courseId).orElseThrow();

        // Obtener los estudiantes
        List<StudentDTO> studentDTOList = studentClient.findAllStudentByCourse(course.getId());

        return StudentByCouseResponse.builder()
                .courseName(course.getName())
                .teacher(course.getTeacher())
                .studentDTOList(studentDTOList)
                .build();
    }
}
