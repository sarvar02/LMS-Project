package uz.isystem.studentweb.Class;

import lombok.*;
import uz.isystem.studentweb.attendence.AttendenceType;
import uz.isystem.studentweb.room.Room;
import uz.isystem.studentweb.userGroup.UserGroup;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = ("classes"))
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = ("room_id"), insertable = false, updatable = false)
    private Room room;

    @Column(name = ("room_id"))
    private Integer roomId;

    @ManyToOne
    @JoinColumn(name = ("user_group_id"), insertable = false, updatable = false)
    private UserGroup userGroup;

    @Column(name = ("user_group_id"))
    private Integer userGroupId;

    @Column(name = ("group_id"))
    private Integer groupId;

    @ManyToOne
    @JoinColumn(name = ("attendence_type_id"), insertable = false, updatable = false)
    private AttendenceType attendenceType;

    @Column(name = ("attendence_type_id"))
    private Integer attendenceTypeId;

    @Column(name = ("reason_message"))
    private String reasonMessage;

    @Column(name = ("name"))
    private String name;

    @Column(name = ("date"))
    private LocalDate date;

    @Column(name = ("status"))
    private Boolean status;

    @Column(name = ("created_at"))
    private LocalDateTime createdAt;

    @Column(name = ("updated_at"))
    private LocalDateTime updatedAt;

    @Column(name = ("deleted_at"))
    private LocalDateTime deletedAt;

}
