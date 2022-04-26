package uz.isystem.studentweb.group;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.isystem.studentweb.course.Course;
import uz.isystem.studentweb.course.CourseDto;
import uz.isystem.studentweb.group.group_type.GroupTypeDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
    private Integer id;
    @NotBlank(message = "name cannot be BLANK")
    private String name;
    private CourseDto courseDto;
    @NotNull
    private Integer courseId;
    private GroupTypeDto groupTypeDto;
    @NotNull
    private Integer groupTypeId;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
