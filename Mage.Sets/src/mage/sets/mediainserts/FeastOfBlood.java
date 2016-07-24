package mage.sets.mediainserts;

import java.util.UUID;

public class FeastOfBlood extends mage.sets.zendikar.FeastOfBlood {
    
    public FeastOfBlood(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "43";
        this.expansionSetCode = "MBP";
    }
    
    public FeastOfBlood(final FeastOfBlood card) {
        super(card);
    }
    
    @Override
    public FeastOfBlood copy() {
        return new FeastOfBlood(this);
    }
}
