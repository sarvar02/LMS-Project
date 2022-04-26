package uz.isystem.studentweb.group;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.isystem.studentweb.course.Course;
import uz.isystem.studentweb.group.group_type.GroupType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "course_id",insertable = false, updatable = false)
    private Course course;

    @Column(name = "course_id")
    private Integer courseId;

    @ManyToOne
    @JoinColumn(name = "group_type_id", insertable = false, updatable = false)
    private GroupType groupType;

    @Column(name = "group_type_id")
    private Integer groupTypeId;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
