package mage.sets.mediainserts;

import java.util.UUID;

public class GraveTitan extends mage.sets.magic2011.GraveTitan {
    
    public GraveTitan(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "35";
        this.expansionSetCode = "MBP";
    }
    
    public GraveTitan(final GraveTitan card) {
        super(card);
    }
    
    @Override
    public GraveTitan copy() {
        return new GraveTitan(this);
    }
}
