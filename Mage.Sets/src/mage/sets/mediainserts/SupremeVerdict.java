package mage.sets.mediainserts;

import java.util.UUID;

public class SupremeVerdict extends mage.sets.returntoravnica.SupremeVerdict {
    
    public SupremeVerdict(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "56";
        this.expansionSetCode = "MBP";
    }
    
    public SupremeVerdict(final SupremeVerdict card) {
        super(card);
    }
    
    @Override
    public SupremeVerdict copy() {
        return new SupremeVerdict(this);
    }
}
