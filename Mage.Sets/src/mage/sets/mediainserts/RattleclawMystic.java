package mage.sets.mediainserts;

import java.util.UUID;

public class RattleclawMystic extends mage.sets.khansoftarkir.RattleclawMystic {
    
    public RattleclawMystic(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "92";
        this.expansionSetCode = "MBP";
    }
    
    public RattleclawMystic(final RattleclawMystic card) {
        super(card);
    }
    
    @Override
    public RattleclawMystic copy() {
        return new RattleclawMystic(this);
    }
}
