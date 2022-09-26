package mage.remote.messages.callback;

import java.util.UUID;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class JoinedTableCallback extends ClientMessage {
    
    private UUID roomId;
    private UUID tableId;
    private UUID chatId;
    private boolean owner;
    private boolean tournament;
    
    public JoinedTableCallback(UUID roomId, UUID tableId, UUID chatId, boolean owner, boolean tournament) {
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
