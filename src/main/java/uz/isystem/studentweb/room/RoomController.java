package uz.isystem.studentweb.room;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<RoomDto> roomDtoList = roomService.getAll();
        return ResponseEntity.ok(roomDtoList);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RoomDto roomDto){
        roomService.create(roomDto);
        return ResponseEntity.ok("New room created !");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        roomService.delete(id);
        return ResponseEntity.ok("Room created !");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id){
        RoomDto roomDto = roomService.get(id);
        return ResponseEntity.ok(roomDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody RoomDto roomDto){
        roomService.update(id, roomDto);
        return ResponseEntity.ok("Room successfully updated !");
    }

}
