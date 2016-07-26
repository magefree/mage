package mage.sets.mediainserts;

import java.util.UUID;

public class RatchetBomb extends mage.sets.magic2014.RatchetBomb {
    
    public RatchetBomb(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "67";
        this.expansionSetCode = "MBP";
    }
    
    public RatchetBomb(final RatchetBomb card) {
        super(card);
    }
    
    @Override
    public RatchetBomb copy() {
        return new RatchetBomb(this);
    }
}
