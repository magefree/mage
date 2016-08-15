package mage.sets.mediainserts;

import java.util.UUID;

public class SylvanCaryatid extends mage.sets.theros.SylvanCaryatid {
    
    public SylvanCaryatid(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "77";
        this.expansionSetCode = "MBP";
    }
    
    public SylvanCaryatid(final SylvanCaryatid card) {
        super(card);
    }
    
    @Override
    public SylvanCaryatid copy() {
        return new SylvanCaryatid(this);
    }
}
