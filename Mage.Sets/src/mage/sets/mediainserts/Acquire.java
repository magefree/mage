package mage.sets.mediainserts;

import java.util.UUID;

public class Acquire extends mage.sets.fifthdawn.Acquire {
    
    public Acquire(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "83";
        this.expansionSetCode = "MBP";
    }
    
    public Acquire(final Acquire card) {
        super(card);
    }
    
    @Override
    public Acquire copy() {
        return new Acquire(this);
    }
}
