package uz.isystem.studentweb.telegrambot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.isystem.studentweb.telegrambot.botapi.TelegramFacade;
import uz.isystem.studentweb.telegrambot.util.CurrentMessage;
import uz.isystem.studentweb.telegrambot.util.MessageType;

@Getter
@Setter
@Component
public class Bot extends TelegramLongPollingBot {
    @Value("${telegram.username}")
    private String username;
    @Value("${telegram.token}")
    private String token;

    private final TelegramFacade telegramFacade;

    public Bot(TelegramFacade telegramFacade) {
        this.telegramFacade = telegramFacade;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        CurrentMessage currentMessage = telegramFacade.handle(update);
        if (currentMessage != null || currentMessage.getType() != null) {
            executeCurrentMessage(currentMessage);
        }
    }

    private void executeCurrentMessage(CurrentMessage currentMessage) {
        MessageType type = currentMessage.getType();
        try {
            switch (type) {
                case SEND_MESSAGE:
                    execute(currentMessage.getSendMessage());
                    break;
                case SEND_PHOTO:
                    execute(currentMessage.getSendPhoto());
                    break;
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
