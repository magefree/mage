package mage.sets.mediainserts;

import java.util.UUID;

public class ChandraPyromaster2 extends mage.sets.magic2015.ChandraPyromaster {
    
    public ChandraPyromaster2(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "102";
        this.expansionSetCode = "MBP";
    }
    
    public ChandraPyromaster2(final ChandraPyromaster2 card) {
        super(card);
    }
    
    @Override
    public ChandraPyromaster2 copy() {
        return new ChandraPyromaster2(this);
    }
}
