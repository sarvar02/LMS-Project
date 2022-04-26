package uz.isystem.studentweb.userGroup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.isystem.studentweb.group.Group;
import uz.isystem.studentweb.group.GroupDto;
import uz.isystem.studentweb.user.User;
import uz.isystem.studentweb.user.UserDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupDto {

    private Integer id;
    private UserDto user;
    @NotNull
    private Integer userId;
    private GroupDto group;
    @NotNull
    private Integer groupId;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
