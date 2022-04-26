package uz.isystem.studentweb.room;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomDto {
    private Integer id;
    @NotBlank(message = "Room name cannot be blank")
    private String name;
    private Boolean status;
}
