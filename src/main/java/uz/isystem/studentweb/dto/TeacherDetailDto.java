package uz.isystem.studentweb.dto;

import lombok.Getter;
import lombok.Setter;
import uz.isystem.studentweb.group.Group;
import uz.isystem.studentweb.user.User;

import java.util.List;

@Getter
@Setter
public class TeacherDetailDto {
    private List<Group> groupList;
    private List<List<User>> userList;
}
