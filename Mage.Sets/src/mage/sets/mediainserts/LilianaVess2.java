package mage.sets.mediainserts;

import java.util.UUID;


public class LilianaVess2 extends mage.sets.magic2011.LilianaVess {
    
    public LilianaVess2(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "101";
        this.expansionSetCode = "MBP";
    }
    
    public LilianaVess2(final LilianaVess2 card) {
        super(card);
    }
    
    @Override
    public LilianaVess2 copy() {
        return new LilianaVess2(this);
    }
}
