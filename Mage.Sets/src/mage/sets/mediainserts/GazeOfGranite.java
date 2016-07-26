package mage.sets.mediainserts;

import java.util.UUID;

public class GazeOfGranite extends mage.sets.dragonsmaze.GazeOfGranite {
    
    public GazeOfGranite(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "81";
        this.expansionSetCode = "MBP";
    }
    
    public GazeOfGranite(final GazeOfGranite card) {
        super(card);
    }
    
    @Override
    public GazeOfGranite copy() {
        return new GazeOfGranite(this);
    }
}
