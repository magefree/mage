

package mage.server;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class RoomImpl implements Room {

    private final UUID chatId;
    private final UUID roomId;

    public RoomImpl() {
        roomId = UUID.randomUUID();
        chatId = ChatManager.instance.createChatSession("Room " + roomId);
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
