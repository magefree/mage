package mage.sets.mediainserts;

import java.util.UUID;

public class BrionStoutarm extends mage.sets.lorwyn.BrionStoutarm {
    
    public BrionStoutarm(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "17";
        this.expansionSetCode = "MBP";
    }
    
    public BrionStoutarm(final BrionStoutarm card) {
        super(card);
    }
    
    @Override
    public BrionStoutarm copy() {
        return new BrionStoutarm(this);
    }
}
