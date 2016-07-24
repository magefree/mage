package mage.sets.mediainserts;

import java.util.UUID;

public class SilverbladePaladin extends mage.sets.commander2014.SilverbladePaladin {
    
    public SilverbladePaladin(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "44";
        this.expansionSetCode = "MBP";
    }
    
    public SilverbladePaladin(final SilverbladePaladin card) {
        super(card);
    }
    
    @Override
    public SilverbladePaladin copy() {
        return new SilverbladePaladin(this);
    }
}
