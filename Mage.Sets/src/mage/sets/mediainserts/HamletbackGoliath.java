package mage.sets.mediainserts;

import java.util.UUID;

public class HamletbackGoliath extends mage.sets.lorwyn.HamletbackGoliath {
    
    public HamletbackGoliath(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "71";
        this.expansionSetCode = "MBP";
    }
    
    public HamletbackGoliath(final HamletbackGoliath card) {
        super(card);
    }
    
    @Override
    public HamletbackGoliath copy() {
        return new HamletbackGoliath(this);
    }
}
