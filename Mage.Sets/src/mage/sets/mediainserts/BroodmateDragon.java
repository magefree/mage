package mage.sets.mediainserts;

import java.util.UUID;

public class BroodmateDragon extends mage.sets.shardsofalara.BroodmateDragon {
    
    public BroodmateDragon(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "19";
        this.expansionSetCode = "MBP";
    }
    
    public BroodmateDragon(final BroodmateDragon card) {
        super(card);
    }
    
    @Override
    public BroodmateDragon copy() {
        return new BroodmateDragon(this);
    }
}
