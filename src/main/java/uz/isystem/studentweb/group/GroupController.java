package uz.isystem.studentweb.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody GroupDto groupDto){
        groupService.create(groupDto);
        return ResponseEntity.ok("New Group Created !");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        GroupDto result = groupService.get(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<GroupDto> groupList = groupService.getAll();
        return ResponseEntity.ok(groupList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        groupService.delete(id);
        return ResponseEntity.ok("Group Deleted !");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @Valid @RequestBody GroupDto groupDto){
        groupService.update(id,groupDto);
        return ResponseEntity.ok("Group successfully updated !");
    }


}
