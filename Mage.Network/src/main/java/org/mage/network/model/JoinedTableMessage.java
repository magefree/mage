package org.mage.network.model;

import java.util.UUID;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class JoinedTableMessage extends ClientMessage {
    
    private UUID roomId;
    private UUID tableId;
    private UUID chatId;
    private boolean owner;
    private boolean tournament;
    
    public JoinedTableMessage(UUID roomId, UUID tableId, UUID chatId, boolean owner, boolean tournament) {
        this.roomId = roomId;
        this.tableId = tableId;
        this.chatId = chatId;
        this.owner = owner;
        this.tournament = tournament;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().joinedTable(roomId, tableId, chatId, owner, tournament);
    }
}
