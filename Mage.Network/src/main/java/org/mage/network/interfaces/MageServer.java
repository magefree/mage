package org.mage.network.interfaces;

import java.util.List;
import java.util.UUID;
import mage.interfaces.ServerState;
import mage.utils.MageVersion;

/**
 *
 * @author BetaSteward
 */
public interface MageServer {
    
    boolean registerClient(String userName, String sessionId, MageVersion version);

    void receiveChatMessage(UUID chatId, String sessionId, String message);
    void joinChat(UUID chatId, String sessionId);
    void leaveChat(UUID chatId, String sessionId);
    UUID getRoomChatId(UUID roomId);
    void receiveBroadcastMessage(String message, String sessionId);
    
    ServerState getServerState();

    public List<String> getServerMessages();
    
}
