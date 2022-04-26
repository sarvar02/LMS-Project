package uz.isystem.studentweb.Class;

import lombok.*;
import uz.isystem.studentweb.attendence.AttendenceTypeDto;
import uz.isystem.studentweb.room.RoomDto;
import uz.isystem.studentweb.userGroup.UserGroupDto;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassDto {
    private Integer id;
    private RoomDto roomDto;
    private Integer roomId;
    private UserGroupDto userGroupDto;
    private Integer userGroupId;
    private AttendenceTypeDto attendenceTypeDto;
    private Integer attendenceTypeId;
    private String name;
    private String reasonMessage;
    private LocalDate date;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
