package uz.isystem.studentweb.attendence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.isystem.studentweb.exception.ServerBadRequestException;
import uz.isystem.studentweb.format.Result;
import uz.isystem.studentweb.format.Root;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendenceTypeService {

    @Autowired
    private AttendenceTypeRepository attendenceTypeRepository;

//    @Autowired
//    private RestTemplate restTemplate;

    public void create(AttendenceTypeDto attendenceTypeDto){
        AttendenceType attendenceType = convertDtoToEntity(attendenceTypeDto, new AttendenceType());
        attendenceTypeRepository.save(attendenceType);
    }

    public AttendenceTypeDto get(Integer id){
        AttendenceType attendenceType = getEntity(id);
        return convertEntityToDto(attendenceType, new AttendenceTypeDto());
    }

    public void delete(Integer id){
        // Cheking...
        getEntity(id);
        // Deleting...
        attendenceTypeRepository.deleteById(id);
    }

    public List<AttendenceTypeDto> getAll(){
        List<AttendenceType> attendenceTypeList = attendenceTypeRepository.findAll();
        if(attendenceTypeList.isEmpty()){
            throw new ServerBadRequestException("Attendence type not exists !");
        }
        return attendenceTypeList.stream().
                map(attendenceType -> convertEntityToDto(attendenceType, new AttendenceTypeDto())).
                collect(Collectors.toList());
    }

    public void update(Integer id, AttendenceTypeDto attendenceTypeDto){
        AttendenceType attendenceType = getEntity(id);
        convertDtoToEntity(attendenceTypeDto, attendenceType);
        attendenceTypeRepository.save(attendenceType);
    }

    // Secondary functions

    public AttendenceType getEntity(Integer id){
        Optional<AttendenceType> optional = attendenceTypeRepository.findById(id);
        if (optional.isEmpty()){
            throw new ServerBadRequestException("Attendence type not found !");
        }
        return optional.get();
    }

    public AttendenceType convertDtoToEntity(AttendenceTypeDto dto, AttendenceType entity){
        entity.setName(dto.getName());
        entity.setReasonMessage(dto.getReasonMEssage());

        return entity;
    }

    public AttendenceTypeDto convertEntityToDto(AttendenceType entity, AttendenceTypeDto dto){
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setReasonMEssage(entity.getReasonMessage());

        return dto;
    }


//    RestTemplate Example

//    public Result getNews(Integer id) {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        Map<String, String> headers = new HashMap<>();
//        headers.put("x-user-agent", "desktop");
//        headers.put("x-proxy-location", "EU");
//        headers.put("x-rapidapi-host", "google-search3.p.rapidapi.com");
//        headers.put("x-rapidapi-key", "ff5be220c0mshb7f618db010a394p15067cjsn205b00792ba2");
//
//        httpHeaders.setAll(headers);
//        HttpEntity<Root> entity = new HttpEntity<>(httpHeaders);
//        Root root = restTemplate.exchange("https://google-search3.p.rapidapi.com/api/v1/search/q=elclassico",
//                HttpMethod.GET, entity, Root.class).getBody();
//
//        return root.getResults().get(id);
//    }
}
