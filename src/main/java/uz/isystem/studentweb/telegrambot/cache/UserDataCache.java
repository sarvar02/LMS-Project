package uz.isystem.studentweb.telegrambot.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache {
    private final Map<Long, UserProfileInfo> userProfileInfoMap = new HashMap<>();
    private final Map<Long, BotState> userProfileStateMap = new HashMap<>();

    public void setUserCurrentBotState(long userId, BotState state) {
        userProfileStateMap.put(userId, state);
    }

    public BotState getUserCurrentBotState(long userId) {
        BotState botState = userProfileStateMap.get(userId);
        if (botState == null) {
            botState = BotState.SHOW_MAIN_MENU;
        }
        return botState;
    }

    public void saveUserProfileInfo(long userId, UserProfileInfo userProfileInfo) {
        userProfileInfoMap.put(userId, userProfileInfo);
    }

    public UserProfileInfo getUserProfileInfo(long userId){
        UserProfileInfo userProfileInfo = userProfileInfoMap.get(userId);
        if (userProfileInfo == null){
            userProfileInfo = new UserProfileInfo();
            userProfileInfo.setStatus(false);
            userProfileInfo.setUserId(userId);
        }
        return userProfileInfo;
    }

    public Boolean checkUserStatus(long userId){
       UserProfileInfo userProfileInfo =  userProfileInfoMap.get(userId);
        if (userProfileInfo == null){
            userProfileInfo = new UserProfileInfo();
            userProfileInfo.setStatus(false);
            userProfileInfo.setUserId(userId);
        }
        return userProfileInfo.getStatus();
    }

}
