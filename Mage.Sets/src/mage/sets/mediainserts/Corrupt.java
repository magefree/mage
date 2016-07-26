package mage.sets.mediainserts;

import java.util.UUID;

public class Corrupt extends mage.sets.magic2014.Corrupt {
    
    public Corrupt(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "64";
        this.expansionSetCode = "MBP";
    }
    
    public Corrupt(final Corrupt card) {
        super(card);
    }
    
    @Override
    public Corrupt copy() {
        return new Corrupt(this);
    }
}
