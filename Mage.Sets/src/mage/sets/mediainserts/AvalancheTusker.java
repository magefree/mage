package mage.sets.mediainserts;

import java.util.UUID;

public class AvalancheTusker extends mage.sets.khansoftarkir.AvalancheTusker {
    
    public AvalancheTusker(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "94";
        this.expansionSetCode = "MBP";
    }
    
    public AvalancheTusker(final AvalancheTusker card) {
        super(card);
    }
    
    @Override
    public AvalancheTusker copy() {
        return new AvalancheTusker(this);
    }
}
