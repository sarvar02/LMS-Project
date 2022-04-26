package uz.isystem.studentweb.telegrambot.botapi.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.isystem.studentweb.telegrambot.botapi.InputMessageHandler;
import uz.isystem.studentweb.telegrambot.cache.BotState;
import uz.isystem.studentweb.telegrambot.service.ReplyMessageService;
import uz.isystem.studentweb.telegrambot.util.CurrentMessage;
import uz.isystem.studentweb.telegrambot.util.MessageType;

@Component
public class AboutHandler implements InputMessageHandler {
    @Autowired
    private ReplyMessageService replyMessageService;

    @Override
    public CurrentMessage handle(Message message) {
        return getAboutMessage(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_ABOUT;
    }

    private CurrentMessage getAboutMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(replyMessageService.getReplyText("reply.about"));
        sendMessage.setChatId(String.valueOf(message.getChatId()));

        CurrentMessage currentMessage = new CurrentMessage();
        currentMessage.setSendMessage(sendMessage);
        currentMessage.setType(MessageType.SEND_MESSAGE);
        return currentMessage;
    }
}
