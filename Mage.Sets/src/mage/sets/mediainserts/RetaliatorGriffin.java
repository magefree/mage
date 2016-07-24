package mage.sets.mediainserts;

import java.util.UUID;

public class RetaliatorGriffin extends mage.sets.alarareborn.RetaliatorGriffin {
    
    public RetaliatorGriffin(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "24";
        this.expansionSetCode = "MBP";
    }
    
    public RetaliatorGriffin(final RetaliatorGriffin card) {
        super(card);
    }
    
    @Override
    public RetaliatorGriffin copy() {
        return new RetaliatorGriffin(this);
    }
}
