package mage.sets.mediainserts;

import java.util.UUID;

public class NightveilSpecter extends mage.sets.gatecrash.NightveilSpecter {
    
    public NightveilSpecter(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "61";
        this.expansionSetCode = "MBP";
    }
    
    public NightveilSpecter(final NightveilSpecter card) {
        super(card);
    }
    
    @Override
    public NightveilSpecter copy() {
        return new NightveilSpecter(this);
    }
}
