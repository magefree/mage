package mage.sets.mediainserts;

import java.util.UUID;

public class AjaniSteadfast extends mage.sets.magic2015.AjaniSteadfast {
    
    public AjaniSteadfast(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "99";
        this.expansionSetCode = "MBP";
    }
    
    public AjaniSteadfast(final AjaniSteadfast card) {
        super(card);
    }
    
    @Override
    public AjaniSteadfast copy() {
        return new AjaniSteadfast(this);
    }
}
