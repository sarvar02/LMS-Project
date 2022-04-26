package uz.isystem.studentweb.usertype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user-type")
public class UserTypeController {

    @Autowired
    private UserTypeService userTypeService;

    public UserTypeController() {

    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserTypeDto userTypeDto){
//        UserType userType1 = modelMapper.map(userTypeDto, UserType.class);
        userTypeService.create(userTypeDto);
        return ResponseEntity.ok("New user type created !");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer id){
        UserTypeDto result = userTypeService.get(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getByName")
    public ResponseEntity<?> getByName(@RequestParam("name") String name){
        List<UserType> result = userTypeService.getByName(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<UserTypeDto> userList = userTypeService.getAll();
        return ResponseEntity.ok(userList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody UserTypeDto userTypeDto){
        UserTypeDto result = userTypeService.update(id, userTypeDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        userTypeService.delete(id);
        return ResponseEntity.ok("User Deleted !\n\nDeleted Time: "+ LocalDateTime.now());
    }

}
