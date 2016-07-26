package mage.sets.mediainserts;

import java.util.UUID;

public class PhyrexianRager extends mage.sets.mirrodinbesieged.PhyrexianRager {
    
    public PhyrexianRager(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "14";
        this.expansionSetCode = "MBP";
    }
    
    public PhyrexianRager(final PhyrexianRager card) {
        super(card);
    }
    
    @Override
    public PhyrexianRager copy() {
        return new PhyrexianRager(this);
    }
}
