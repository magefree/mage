package mage.sets.mediainserts;

import java.util.UUID;

public class ChandraPyromaster extends mage.sets.magic2015.ChandraPyromaster {
    
    public ChandraPyromaster(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "75";
        this.expansionSetCode = "MBP";
    }
    
    public ChandraPyromaster(final ChandraPyromaster card) {
        super(card);
    }
    
    @Override
    public ChandraPyromaster copy() {
        return new ChandraPyromaster(this);
    }
}
