package mage.sets.mediainserts;

import java.util.UUID;

public class EidolonOfBlossoms extends mage.sets.journeyintonyx.EidolonOfBlossoms {
    
    public EidolonOfBlossoms(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "85";
        this.expansionSetCode = "MBP";
    }
    
    public EidolonOfBlossoms(final EidolonOfBlossoms card) {
        super(card);
    }
    
    @Override
    public EidolonOfBlossoms copy() {
        return new EidolonOfBlossoms(this);
    }
}
