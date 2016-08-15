package mage.sets.mediainserts;

import java.util.UUID;

public class BreathOfMalfegor extends mage.sets.alarareborn.BreathOfMalfegor {
    
    public BreathOfMalfegor(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "58";
        this.expansionSetCode = "MBP";
    }
    
    public BreathOfMalfegor(final BreathOfMalfegor card) {
        super(card);
    }
    
    @Override
    public BreathOfMalfegor copy() {
        return new BreathOfMalfegor(this);
    }
}
