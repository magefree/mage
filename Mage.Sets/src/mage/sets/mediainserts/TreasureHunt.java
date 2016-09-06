package mage.sets.mediainserts;

import java.util.UUID;

public class TreasureHunt extends mage.sets.worldwake.TreasureHunt {
    
    public TreasureHunt(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "38";
        this.expansionSetCode = "MBP";
    }
    
    public TreasureHunt(final TreasureHunt card) {
        super(card);
    }
    
    @Override
    public TreasureHunt copy() {
        return new TreasureHunt(this);
    }
}
