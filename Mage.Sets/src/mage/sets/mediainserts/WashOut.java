package mage.sets.mediainserts;

import java.util.UUID;

public class WashOut extends mage.sets.invasion.WashOut {
    
    public WashOut(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "82";
        this.expansionSetCode = "MBP";
    }
    
    public WashOut(final WashOut card) {
        super(card);
    }
    
    @Override
    public WashOut copy() {
        return new WashOut(this);
    }
}
