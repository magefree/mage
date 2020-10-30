package mage.server.managers;

import mage.game.Game;
import mage.server.ChatSession;
import mage.server.DisconnectReason;
import mage.server.exceptions.UserNotFoundException;
import mage.view.ChatMessage;

import java.util.List;
import java.util.UUID;

public interface ChatManager {
    UUID createChatSession(String info);

    void joinChat(UUID chatId, UUID userId);

    void clearUserMessageStorage();

    void leaveChat(UUID chatId, UUID userId);

    void destroyChatSession(UUID chatId);

    void broadcast(UUID chatId, String userName, String message, ChatMessage.MessageColor color, boolean withTime, Game game, ChatMessage.MessageType messageType, ChatMessage.SoundToPlay soundToPlay);

    void broadcast(UUID userId, String message, ChatMessage.MessageColor color) throws UserNotFoundException;

    void sendReconnectMessage(UUID userId);

    void sendLostConnectionMessage(UUID userId, DisconnectReason reason);

    void sendMessageToUserChats(UUID userId, String message);

    void removeUser(UUID userId, DisconnectReason reason);

    List<ChatSession> getChatSessions();
}
