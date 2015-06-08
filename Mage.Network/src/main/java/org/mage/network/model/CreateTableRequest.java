package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;
import mage.game.match.MatchOptions;

/**
 *
 * @author BetaSteward
 */
public class CreateTableRequest implements Serializable {
    
    private UUID roomId;
    private MatchOptions options;
    
    public CreateTableRequest(UUID roomId, MatchOptions options) {
        this.roomId = roomId;
        this.options = options;
    }
    
    public UUID getRoomId() {
        return roomId;
    }

    public MatchOptions getMatchOptions() {
        return options;
    }
    
}
