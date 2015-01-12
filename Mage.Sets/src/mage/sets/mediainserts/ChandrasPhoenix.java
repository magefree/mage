package mage.sets.mediainserts;

import java.util.UUID;

public class ChandrasPhoenix extends mage.sets.magic2012.ChandrasPhoenix {
    
    public ChandrasPhoenix(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 37;
        this.expansionSetCode = "MBP";
    }
    
    public ChandrasPhoenix(final ChandrasPhoenix card) {
        super(card);
    }
    
    @Override
    public ChandrasPhoenix copy() {
        return new ChandrasPhoenix(this);
    }
}
