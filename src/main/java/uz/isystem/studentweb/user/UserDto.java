package uz.isystem.studentweb.user;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.isystem.studentweb.usertype.UserType;
import uz.isystem.studentweb.usertype.UserTypeDto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Integer id;
    private UserTypeDto userTypeDto;
    @NotNull(message = "UserTypeId cannot be NULL")
    private Integer userTypeId;
    private Long userId;
    private Long chatId;
    @NotNull(message = "Username cannot be NULL")
    private String userName;
    @NotBlank(message = "Phone canoot be BLANK")
    private String phone;
    private String firstName;
    private String lastName;
    private String password;
    private String token;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
