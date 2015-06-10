package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class JoinedTableMessage implements Serializable {
    
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
    
    public UUID getRoomId() {
        return roomId;
    }

    public UUID getTableId() {
        return tableId;
    }

    public UUID getChatId() {
        return chatId;
    }

    public boolean isOwner() {
        return owner;
    }
    
    public boolean isTournament() {
        return tournament;
    }
}
