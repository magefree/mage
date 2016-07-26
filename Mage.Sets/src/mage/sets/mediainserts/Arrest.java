package mage.sets.mediainserts;

import java.util.UUID;

public class Arrest extends mage.sets.mirrodin.Arrest {
    
    public Arrest(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "53";
        this.expansionSetCode = "MBP";
    }
    
    public Arrest(final Arrest card) {
        super(card);
    }
    
    @Override
    public Arrest copy() {
        return new Arrest(this);
    }
}
