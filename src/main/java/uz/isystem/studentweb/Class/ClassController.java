package uz.isystem.studentweb.Class;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/class")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCLassById(@PathVariable Integer id){
        return ResponseEntity.ok(classService.get(id));
    }

    @PostMapping
    public ResponseEntity<?> createNewClass(@RequestBody ClassDto classDto){
        return ResponseEntity.ok(classService.create(classDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClassById(@PathVariable Integer id){
        return ResponseEntity.ok(classService.delete(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCLass(@PathVariable Integer id,
                                         @RequestBody ClassDto classDto){
        return ResponseEntity.ok(classService.update(id, classDto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getClasses(){
        return ResponseEntity.ok(classService.getAll());
    }

    @GetMapping("/teacher")
    public ResponseEntity<?> getClassesByGroupId(@RequestParam("groupdId") Integer groupId){
        return ResponseEntity.ok(classService.getClassesByGroupdId(groupId));
    }
}
