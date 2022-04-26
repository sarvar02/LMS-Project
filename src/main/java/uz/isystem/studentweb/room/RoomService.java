package uz.isystem.studentweb.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.isystem.studentweb.exception.ServerBadRequestException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public void create(RoomDto roomDto){
        Room room = convertDtoToEntity(roomDto, new Room());
        room.setStatus(true);
        roomRepository.save(room);
    }

    public RoomDto get(Integer id){
        Room room = getEntity(id);
        return convertEntityToDto(room, new RoomDto());
    }

    public void delete(Integer id){
        // Cheking...
        getEntity(id);
        // Delete
        roomRepository.deleteById(id);
    }

    public List<RoomDto> getAll(){
        List<Room> roomList = roomRepository.findAll();
        if(roomList.isEmpty()){
            throw new ServerBadRequestException("There aren't any rooms yet !");
        }

        return roomList.stream().
                map(room -> convertEntityToDto(room, new RoomDto())).
                collect(Collectors.toList());
    }

    public void update(Integer id, RoomDto roomDto){
        Room room = getEntity(id);
        convertDtoToEntity(roomDto, room);
        roomRepository.save(room);
    }


    // Secondary functions

    public Room getEntity(Integer id){
        Optional<Room> optional = roomRepository.findById(id);
        if(optional.isEmpty()){
            throw new ServerBadRequestException("Room Not Found !");
        }
        return optional.get();
    }

    public RoomDto convertEntityToDto(Room room, RoomDto roomDto){
        roomDto.setId(room.getId());
        roomDto.setName(room.getName());
        roomDto.setStatus(room.getStatus());
        return roomDto;
    }

    public Room convertDtoToEntity(RoomDto roomDto, Room room){
        room.setName(roomDto.getName());
        return room;
    }

}
