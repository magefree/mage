package mage.sets.mediainserts;

import java.util.UUID;

public class FatedConflagration extends mage.sets.bornofthegods.FatedConflagration {
    
    public FatedConflagration(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "79";
        this.expansionSetCode = "MBP";
    }
    
    public FatedConflagration(final FatedConflagration card) {
        super(card);
    }
    
    @Override
    public FatedConflagration copy() {
        return new FatedConflagration(this);
    }
}
