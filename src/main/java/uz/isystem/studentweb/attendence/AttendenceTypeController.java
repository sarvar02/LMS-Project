package uz.isystem.studentweb.attendence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/attendence-type")
public class AttendenceTypeController {

    @Autowired
    private AttendenceTypeService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AttendenceTypeDto attendenceTypeDto){
        service.create(attendenceTypeDto);
        return ResponseEntity.ok("New attendence type created !");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        AttendenceTypeDto attendenceTypeDto = service.get(id);
        return ResponseEntity.ok(attendenceTypeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody AttendenceTypeDto attendenceTypeDto){
        service.update(id, attendenceTypeDto);
        return ResponseEntity.ok("Attendence type successfully updated !");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.ok("Attendence type successfully deleted !");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<AttendenceTypeDto> attendenceTypeDtoList = service.getAll();
        return ResponseEntity.ok(attendenceTypeDtoList);
    }




//        RestTemplate Example
//    @GetMapping("/news/{id}")
//    public ResponseEntity<?> getNews(@PathVariable Integer id){
//        Result result = service.getNews(id);
//        return ResponseEntity.ok(result);
//    }
}
