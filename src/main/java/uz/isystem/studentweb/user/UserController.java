package uz.isystem.studentweb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isystem.studentweb.util.SpringSecurityUtil;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Yangi user yaratish
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserDto userDto){
        userService.create(userDto);
        return ResponseEntity.ok("User created !");
    }

    // ID bo'yicha olish
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer id){
        UserDto result = userService.get(id);
        return ResponseEntity.badRequest().body(result);
    }

    // Barcha Userlarni olish
    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<UserDto> userList = userService.getAll();
        return ResponseEntity.ok(userList);
    }

    // ID bo'yicha userlarni update qilish
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody UserDto userDto){
        User result = userService.update(id, userDto);
        return ResponseEntity.ok(result);
    }

    // ID bo'yicha userlarni deleted qilish
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        Boolean result = userService.delete(id);
        return ResponseEntity.ok("User success fully deleted !\n\nDeleted Time: "+ LocalDateTime.now());
    }

    //User Info
    @GetMapping("/info")
    public ResponseEntity<?> getInfo(){
        return ResponseEntity.ok(userService.get(SpringSecurityUtil.getUserId()));
    }

}
