package mage.sets.mediainserts;

import java.util.UUID;

public class BloodthroneVampire extends mage.sets.riseoftheeldrazi.BloodthroneVampire {
    
    public BloodthroneVampire(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "31";
        this.expansionSetCode = "MBP";
    }
    
    public BloodthroneVampire(final BloodthroneVampire card) {
        super(card);
    }
    
    @Override
    public BloodthroneVampire copy() {
        return new BloodthroneVampire(this);
    }
}
