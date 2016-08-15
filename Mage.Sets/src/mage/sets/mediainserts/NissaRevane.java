package mage.sets.mediainserts;

import java.util.UUID;

public class NissaRevane extends mage.sets.zendikar.NissaRevane {
    
    public NissaRevane(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "27";
        this.expansionSetCode = "MBP";
    }
    
    public NissaRevane(final NissaRevane card) {
        super(card);
    }
    
    @Override
    public NissaRevane copy() {
        return new NissaRevane(this);
    }
}
