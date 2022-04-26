package uz.isystem.studentweb.telegrambot.botapi.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.isystem.studentweb.telegrambot.botapi.InputMessageHandler;
import uz.isystem.studentweb.telegrambot.cache.BotState;
import uz.isystem.studentweb.telegrambot.cache.UserDataCache;
import uz.isystem.studentweb.telegrambot.service.ReplyMessageService;
import uz.isystem.studentweb.telegrambot.util.CurrentMessage;
import uz.isystem.studentweb.telegrambot.util.MessageType;

import java.util.LinkedList;
import java.util.List;

@Component
public class MainMenuHandler implements InputMessageHandler {
    @Autowired
    private ReplyMessageService replyMessageService;
    @Autowired
    private UserDataCache userDataCache;

    @Override
    public CurrentMessage handle(Message message) {
        return getMainMenuMessage(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_MAIN_MENU;
    }

    private CurrentMessage getMainMenuMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(replyMessageService.getReplyText("reply.mainMenu"));
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        if (userDataCache.checkUserStatus(message.getFrom().getId())){
            sendMessage.setReplyMarkup(getMainMenuMarkupForAccess());
        }else{
            sendMessage.setReplyMarkup(getMainMenuMarkup());
        }
        CurrentMessage currentMessage = new CurrentMessage();
        currentMessage.setSendMessage(sendMessage);
        currentMessage.setType(MessageType.SEND_MESSAGE);
        return currentMessage;
    }

    private ReplyKeyboard getMainMenuMarkup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        KeyboardButton about = new KeyboardButton();
        about.setText(replyMessageService.getReplyText("button.about"));
        KeyboardButton login = new KeyboardButton();
        login.setText(replyMessageService.getReplyText("button.login"));

        KeyboardRow firstKeyboardRow = new KeyboardRow();
        firstKeyboardRow.add(about);
        firstKeyboardRow.add(login);

        List<KeyboardRow> keyboardRows = new LinkedList<>();
        keyboardRows.add(firstKeyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboard getMainMenuMarkupForAccess(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        KeyboardButton info = new KeyboardButton();
        info.setText(replyMessageService.getReplyText("button.info"));

        KeyboardRow firstKeyboardRow = new KeyboardRow();
        firstKeyboardRow.add(info);

        List<KeyboardRow> keyboardRows = new LinkedList<>();
        keyboardRows.add(firstKeyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }


}
