package mage.sets.mediainserts;

import java.util.UUID;

public class BonescytheSliver extends mage.sets.magic2014.BonescytheSliver {
    
    public BonescytheSliver(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "68";
        this.expansionSetCode = "MBP";
    }
    
    public BonescytheSliver(final BonescytheSliver card) {
        super(card);
    }
    
    @Override
    public BonescytheSliver copy() {
        return new BonescytheSliver(this);
    }
}
