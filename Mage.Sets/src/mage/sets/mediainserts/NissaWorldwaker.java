package mage.sets.mediainserts;

import java.util.UUID;

public class NissaWorldwaker extends mage.sets.magic2015.NissaWorldwaker {
    
    public NissaWorldwaker(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "103";
        this.expansionSetCode = "MBP";
    }
    
    public NissaWorldwaker(final NissaWorldwaker card) {
        super(card);
    }
    
    @Override
    public NissaWorldwaker copy() {
        return new NissaWorldwaker(this);
    }
}
