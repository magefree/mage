package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;
import mage.game.match.MatchOptions;

/**
 *
 * @author BetaSteward
 */
public class CreateTableRequest extends RoomRequest {
    
    private MatchOptions options;
    
    public CreateTableRequest(UUID roomId, MatchOptions options) {
        super(roomId);
        this.options = options;
    }
    
    public MatchOptions getMatchOptions() {
        return options;
    }
    
}
