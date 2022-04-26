package uz.isystem.studentweb.telegrambot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.isystem.studentweb.telegrambot.botapi.InputMessageHandler;
import uz.isystem.studentweb.telegrambot.util.CurrentMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    @Autowired
    private UserDataCache userDataCache;

    private final Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> inputMessageHandlers) {
        inputMessageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public CurrentMessage processInputMessage(BotState currentState, Message message) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        if (currentState.equals(BotState.LOGIN)
                || currentState.equals(BotState.INPUT_PASSWORD)
                || currentState.equals(BotState.INPUT_PHONE)
                || currentState.equals(BotState.CONFIRM)) currentState = BotState.LOGIN;

        return messageHandlers.get(currentState);
    }
}
