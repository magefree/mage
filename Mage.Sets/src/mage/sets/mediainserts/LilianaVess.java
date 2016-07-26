package mage.sets.mediainserts;

import java.util.UUID;

public class LilianaVess extends mage.sets.lorwyn.LilianaVess {
    
    public LilianaVess(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "30";
        this.expansionSetCode = "MBP";
    }
    
    public LilianaVess(final LilianaVess card) {
        super(card);
    }
    
    @Override
    public LilianaVess copy() {
        return new LilianaVess(this);
    }
}
