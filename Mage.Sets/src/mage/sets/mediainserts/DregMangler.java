package mage.sets.mediainserts;

import java.util.UUID;

public class DregMangler extends mage.sets.returntoravnica.DregMangler {
    
    public DregMangler(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "55";
        this.expansionSetCode = "MBP";
    }
    
    public DregMangler(final DregMangler card) {
        super(card);
    }
    
    @Override
    public DregMangler copy() {
        return new DregMangler(this);
    }
}
