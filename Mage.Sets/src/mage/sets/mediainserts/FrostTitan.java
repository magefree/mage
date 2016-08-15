package mage.sets.mediainserts;

import java.util.UUID;

public class FrostTitan extends mage.sets.magic2011.FrostTitan {
    
    public FrostTitan(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "34";
        this.expansionSetCode = "MBP";
    }
    
    public FrostTitan(final FrostTitan card) {
        super(card);
    }
    
    @Override
    public FrostTitan copy() {
        return new FrostTitan(this);
    }
}
