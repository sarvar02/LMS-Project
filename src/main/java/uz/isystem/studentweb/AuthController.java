package uz.isystem.studentweb;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isystem.studentweb.dto.AuthDto;
import uz.isystem.studentweb.dto.RegistrationDto;
import uz.isystem.studentweb.service.AuthService;
import uz.isystem.studentweb.user.User;
import uz.isystem.studentweb.user.UserDto;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthDto dto){
        UserDto token = authService.auth(dto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationDto dto){
        authService.register(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validation/{token}")
    public ResponseEntity<?> validate(@PathVariable("token") String token){
        User user = authService.verification(token);
        return ResponseEntity.ok(user);
    }
}
