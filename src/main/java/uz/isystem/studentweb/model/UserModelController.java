package uz.isystem.studentweb.model;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.isystem.studentweb.course.Course;
import uz.isystem.studentweb.course.CourseDto;
import uz.isystem.studentweb.course.CourseService;
import uz.isystem.studentweb.user.User;
import uz.isystem.studentweb.user.UserDto;
import uz.isystem.studentweb.user.UserService;

import java.util.List;

@Controller
@RequestMapping("/api")
public class UserModelController {

    private final UserService userService;
    private final CourseService courseService;

    public UserModelController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("/users")
    public String getUsers(Model model){
        List<UserDto> userList;
        userList = userService.getAll();
        model.addAttribute("users", userList);
        model.addAttribute("title", "This is users' list !");
        model.addAttribute("user", new UserDto());
        return "users";
    }

    @GetMapping("/courses")
    public String getCourses(Model model){
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("course", new CourseDto());
        model.addAttribute("title", "This is courses' list !");
        return "courses";
    }

//    CREATE COURSE
    @PostMapping("/course/create")
    public String create(@ModelAttribute("course") CourseDto courseDto,
                         Model model){
        courseService.create(courseDto);
        model.addAttribute("courses", courseService.getAll());
        return "redirect:/api/courses";
    }

//    CREATE USER
    @PostMapping("/user/create")
    public String createUser(@ModelAttribute("user") UserDto userDto,
                             Model model){
        userDto.setUserTypeId(1);
        userService.create(userDto);
        return "redirect:/api/users";
    }

//    DELETE USER
    @PostMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model){
        System.out.println("hello");
        userService.delete(id);
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("title", "This is courses' list !");
        return "redirect:/api/users";
    }

//    DELETE COURSE
    @PostMapping("/course/{id}")
    public String deleteCourse(@PathVariable("id") Integer id, Model model){
        courseService.delete(id);
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("course", new CourseDto());
        return "redirect:/api/courses";
    }

}
