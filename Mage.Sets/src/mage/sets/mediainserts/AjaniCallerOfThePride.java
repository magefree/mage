package mage.sets.mediainserts;

import java.util.UUID;

public class AjaniCallerOfThePride extends mage.sets.magic2013.AjaniCallerOfThePride {
    
    public AjaniCallerOfThePride(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "72";
        this.expansionSetCode = "MBP";
    }
    
    public AjaniCallerOfThePride(final AjaniCallerOfThePride card) {
        super(card);
    }
    
    @Override
    public AjaniCallerOfThePride copy() {
        return new AjaniCallerOfThePride(this);
    }
}
