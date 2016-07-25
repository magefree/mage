package mage.sets.mediainserts;

import java.util.UUID;

public class AngelOfGlorysRise extends mage.sets.avacynrestored.AngelOfGlorysRise {
    
    public AngelOfGlorysRise(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "59";
        this.expansionSetCode = "MBP";
    }
    
    public AngelOfGlorysRise(final AngelOfGlorysRise card) {
        super(card);
    }
    
    @Override
    public AngelOfGlorysRise copy() {
        return new AngelOfGlorysRise(this);
    }
}
