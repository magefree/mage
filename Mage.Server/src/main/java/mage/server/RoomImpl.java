package mage.server;

import mage.server.managers.IChatManager;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class RoomImpl implements Room {

    private final UUID chatId;
    private final UUID roomId;

    public RoomImpl(IChatManager chatManager) {
        roomId = UUID.randomUUID();
        chatId = chatManager.createChatSession("Room " + roomId);
    }

    /**
     * @return the chatId
     */
    @Override
    public UUID getChatId() {
        return chatId;
    }

    /**
     * @return the roomId
     */
    @Override
    public UUID getRoomId() {
        return roomId;
    }


}
