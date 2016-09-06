package mage.sets.mediainserts;

import java.util.UUID;

public class PrimordialHydra extends mage.sets.magic2012.PrimordialHydra {
    
    public PrimordialHydra(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "49";
        this.expansionSetCode = "MBP";
    }
    
    public PrimordialHydra(final PrimordialHydra card) {
        super(card);
    }
    
    @Override
    public PrimordialHydra copy() {
        return new PrimordialHydra(this);
    }
}
