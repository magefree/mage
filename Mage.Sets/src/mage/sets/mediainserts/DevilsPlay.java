package mage.sets.mediainserts;

import java.util.UUID;

public class DevilsPlay extends mage.sets.innistrad.DevilsPlay {
    
    public DevilsPlay(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "40";
        this.expansionSetCode = "MBP";
    }
    
    public DevilsPlay(final DevilsPlay card) {
        super(card);
    }
    
    @Override
    public DevilsPlay copy() {
        return new DevilsPlay(this);
    }
}
