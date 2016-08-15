package mage.sets.mediainserts;

import java.util.UUID;

public class ChandrasFury extends mage.sets.magic2013.ChandrasFury {
    
    public ChandrasFury(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "65";
        this.expansionSetCode = "MBP";
    }
    
    public ChandrasFury(final ChandrasFury card) {
        super(card);
    }
    
    @Override
    public ChandrasFury copy() {
        return new ChandrasFury(this);
    }
}
