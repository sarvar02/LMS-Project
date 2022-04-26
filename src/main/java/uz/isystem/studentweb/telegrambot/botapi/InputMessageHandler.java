package uz.isystem.studentweb.telegrambot.botapi;

import org.telegram.telegrambots.meta.api.objects.Message;
import uz.isystem.studentweb.telegrambot.cache.BotState;
import uz.isystem.studentweb.telegrambot.util.CurrentMessage;

public interface InputMessageHandler {
    CurrentMessage handle(Message message);

    BotState getHandlerName();
}
