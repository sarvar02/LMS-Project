package uz.isystem.studentweb.userGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isystem.studentweb.dto.TeacherDetailDto;
import uz.isystem.studentweb.userGroup.UserGroup;
import uz.isystem.studentweb.userGroup.UserGroupService;
import uz.isystem.studentweb.util.SpringSecurityUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user-group")
public class UserGroupController {
    @Autowired
    private UserGroupService userGroupService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserGroupDto userGroupDto){
        userGroupService.create(userGroupDto);
        return ResponseEntity.ok("New User Group Created !");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        UserGroupDto result = userGroupService.get(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<UserGroupDto> userGroupList = userGroupService.findAll();
        return ResponseEntity.ok(userGroupList);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable("id") Integer id){
        userGroupService.delete(id);
        return ResponseEntity.ok("User group deleted !");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @Valid @RequestBody UserGroupDto userGroupDto){
        userGroupService.update(id, userGroupDto);
        return ResponseEntity.ok("User group updated !");
    }

    @GetMapping("/teacher")
    public ResponseEntity<?> getGroupForTeacher(){
        List<UserGroup> result = userGroupService.findAllForTeacher(SpringSecurityUtil.getUserId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("teacher/students")
    public ResponseEntity<?> getStudents(){
        TeacherDetailDto result = userGroupService.findStudentsByTeacherId(SpringSecurityUtil.getUserId());
        return ResponseEntity.ok(result);
    }
}
