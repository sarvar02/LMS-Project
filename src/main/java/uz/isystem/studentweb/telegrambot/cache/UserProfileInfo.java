package uz.isystem.studentweb.telegrambot.cache;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileInfo {
    private Long userId;
    private String username;
    private String phone;
    private String password;
    private Boolean status;
}
