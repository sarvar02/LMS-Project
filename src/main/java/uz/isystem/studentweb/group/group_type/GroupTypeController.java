package uz.isystem.studentweb.group.group_type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/group-type")
public class GroupTypeController {

    @Autowired
    private GroupTypeService groupTypeService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody GroupTypeDto groupTypeDto){
        groupTypeService.create(groupTypeDto);
        return ResponseEntity.ok("New Group Type created !");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        GroupTypeDto groupTypeDto = groupTypeService.get(id);
        return ResponseEntity.ok(groupTypeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody GroupTypeDto groupTypeDto){
        groupTypeService.update(id, groupTypeDto);
        return ResponseEntity.ok("Group type successfully updated !");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        groupTypeService.delete(id);
        return ResponseEntity.ok("Group type deleted !");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<GroupTypeDto> groupTypeDtos = groupTypeService.getAll();
        return ResponseEntity.ok(groupTypeDtos);
    }

}
