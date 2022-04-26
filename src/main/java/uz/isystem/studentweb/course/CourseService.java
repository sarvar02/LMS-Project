package uz.isystem.studentweb.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.isystem.studentweb.exception.CourseException;
import uz.isystem.studentweb.course.Course;
import uz.isystem.studentweb.course.CourseRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;


    public void create(CourseDto courseDto) {
        Course course = convertDtoToEntity(courseDto, new Course());
        course.setCreatedAt(LocalDateTime.now());
        course.setStatus(true);
        courseRepository.save(course);
    }

    public CourseDto getOne(Integer id){
        Course course = getEntity(id);

        return convertEntityToDto(course);
    }

    public List<CourseDto> getAll() {
        List<Course> courseList = courseRepository.findAllByDeletedAtIsNull();
//        if(courseList.isEmpty()){
//            throw new CourseException("Courses Not Found !");
//        }
        return courseList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }


    public void delete(Integer id) {
        Course course = getEntity(id);
        course.setDeletedAt(LocalDateTime.now());
        course.setStatus(false);
        courseRepository.save(course);
    }



    public void update(Integer id, CourseDto courseDto) {
        Course entity = getEntity(id);

        convertDtoToEntity(courseDto, entity);
        entity.setUpdatedAt(LocalDateTime.now());
        courseRepository.save(entity);
    }



    //Secondary function
    public Course getEntity(Integer id){
        Optional<Course> optional = courseRepository.findById(id);
        if(optional.isEmpty() || optional.get().getDeletedAt() != null){
            throw new CourseException("Course not found !");
        }
        return optional.get();
    }

    public Course convertDtoToEntity(CourseDto courseDto, Course course){
        course.setName(courseDto.getName());
        return course;
    }

    public CourseDto convertEntityToDto(Course course){
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setName(course.getName());
        courseDto.setStatus(course.getStatus());
        courseDto.setCreatedAt(course.getCreatedAt());
        courseDto.setUpdatedAt(course.getUpdatedAt());
        courseDto.setDeletedAt(course.getDeletedAt());
        return courseDto;
    }
}
