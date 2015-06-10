package org.mage.network.interfaces;

import java.util.UUID;
import mage.interfaces.ServerState;
import mage.view.ChatMessage;
import org.mage.network.model.MessageType;

/**
 *
 * @author BetaSteward
 */
public interface MageClient {
    
    void connected(String message);
    void disconnected(boolean error);
    
    void inform(String title, String message, MessageType type);
    
    void receiveChatMessage(UUID chatId, ChatMessage message);
    void receiveBroadcastMessage(String message);

    void clientRegistered(ServerState state);
    ServerState getServerState();
    
    void joinedTable(UUID roomId, UUID tableId, UUID chatId, boolean owner, boolean tournament);
}
