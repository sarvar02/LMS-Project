package uz.isystem.studentweb.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CourseDto courseDto){
        courseService.create(courseDto);
        return ResponseEntity.ok("New Course created !");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        CourseDto courseDto = courseService.getOne(id);
        return ResponseEntity.ok(courseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<CourseDto> courseList = courseService.getAll();
        return ResponseEntity.ok(courseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        courseService.delete(id);
        return ResponseEntity.ok("Course Deleted !");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody CourseDto courseDto){
        courseService.update(id,courseDto);
        return ResponseEntity.ok("Course Updated");
    }

}
