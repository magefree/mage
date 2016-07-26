package mage.sets.mediainserts;

import java.util.UUID;

public class SunblastAngel extends mage.sets.scarsofmirrodin.SunblastAngel {
    
    public SunblastAngel(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "47";
        this.expansionSetCode = "MBP";
    }
    
    public SunblastAngel(final SunblastAngel card) {
        super(card);
    }
    
    @Override
    public SunblastAngel copy() {
        return new SunblastAngel(this);
    }
}
