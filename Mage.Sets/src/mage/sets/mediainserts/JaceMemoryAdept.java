package mage.sets.mediainserts;

import java.util.UUID;

public class JaceMemoryAdept extends mage.sets.magic2012.JaceMemoryAdept {
    
    public JaceMemoryAdept(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "73";
        this.expansionSetCode = "MBP";
    }
    
    public JaceMemoryAdept(final JaceMemoryAdept card) {
        super(card);
    }
    
    @Override
    public JaceMemoryAdept copy() {
        return new JaceMemoryAdept(this);
    }
}
