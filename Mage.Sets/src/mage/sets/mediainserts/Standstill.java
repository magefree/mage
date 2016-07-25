package mage.sets.mediainserts;

import java.util.UUID;

public class Standstill extends mage.sets.odyssey.Standstill {
    
    public Standstill(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "57";
        this.expansionSetCode = "MBP";
    }
    
    public Standstill(final Standstill card) {
        super(card);
    }
    
    @Override
    public Standstill copy() {
        return new Standstill(this);
    }
}
