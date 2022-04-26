package uz.isystem.studentweb.telegrambot.botapi.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.isystem.studentweb.telegrambot.botapi.InputMessageHandler;
import uz.isystem.studentweb.telegrambot.cache.BotState;
import uz.isystem.studentweb.telegrambot.cache.UserDataCache;
import uz.isystem.studentweb.telegrambot.cache.UserProfileInfo;
import uz.isystem.studentweb.telegrambot.service.ReplyMessageService;
import uz.isystem.studentweb.telegrambot.util.CurrentMessage;
import uz.isystem.studentweb.telegrambot.util.MessageType;

import java.util.LinkedList;
import java.util.List;

@Component
public class LoginHandler implements InputMessageHandler {
    @Autowired
    private UserDataCache userDataCache;
    @Autowired
    private ReplyMessageService replyMessageService;

    @Override
    public CurrentMessage handle(Message message) {
        return processLogin(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.LOGIN;
    }

    private CurrentMessage processLogin(Message message) {
        String requestText = message.getText();
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();

        UserProfileInfo user = userDataCache.getUserProfileInfo(userId);
        BotState state = userDataCache.getUserCurrentBotState(userId);

        SendMessage response = null;
        CurrentMessage currentMessage = new CurrentMessage();

        if (state.equals(BotState.LOGIN)) {
            response = replyMessageService.getReplyMessage(chatId, "reply.getPhone");
            response.setReplyMarkup(getPhoneMarkup());
            user.setUsername(message.getFrom().getUserName());
            userDataCache.setUserCurrentBotState(userId, BotState.INPUT_PHONE);
        }
        if (state.equals(BotState.INPUT_PHONE)) {
            response = replyMessageService.getReplyMessage(chatId, "reply.getPassword");
            response.setReplyMarkup(new ReplyKeyboardRemove(true, true));
            user.setPhone(requestText);
            userDataCache.setUserCurrentBotState(userId, BotState.INPUT_PASSWORD);
        }

        if (state.equals(BotState.INPUT_PASSWORD)) {
            user.setPassword(requestText);
            if (processCheck(user)) {
                response = replyMessageService.getReplyMessage(chatId, "reply.trueLogin");
                user.setStatus(true);
                userDataCache.setUserCurrentBotState(chatId, BotState.SHOW_MAIN_MENU);
            } else {
                response = replyMessageService.getReplyMessage(chatId, "reply.falseLogin");
                userDataCache.setUserCurrentBotState(chatId, BotState.SHOW_MAIN_MENU);
            }
        }

        currentMessage.setSendMessage(response);
        currentMessage.setType(MessageType.SEND_MESSAGE);
        userDataCache.saveUserProfileInfo(userId, user);
        return currentMessage;
    }

    private boolean processCheck(UserProfileInfo user) {
        return user.getPhone().equals("+998997241330") && user.getPassword().equals("root");
    }

    private ReplyKeyboard getPhoneMarkup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);

        KeyboardButton phone = new KeyboardButton();
        phone.setText(replyMessageService.getReplyText("button.sharePhone"));
        phone.setRequestContact(true);

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(phone);

        List<KeyboardRow> keyboardRows = new LinkedList<>();
        keyboardRows.add(keyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

}
