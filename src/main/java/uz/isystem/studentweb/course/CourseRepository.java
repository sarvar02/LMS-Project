package uz.isystem.studentweb.course;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.isystem.studentweb.course.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Integer> {
    Optional<Course> findByName(String name);
    List<Course> findAllByDeletedAtIsNull();
}
