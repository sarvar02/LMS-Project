package uz.isystem.studentweb.attendence;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttendenceTypeDto {
    private Integer id;
    @NotBlank(message = "name cannot be blank")
    private String name;
    private String reasonMEssage;
}
