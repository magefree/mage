package mage.sets.mediainserts;

import java.util.UUID;

public class SageOfTheInwardEye extends mage.sets.khansoftarkir.SageOfTheInwardEye {
    
    public SageOfTheInwardEye(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "97";
        this.expansionSetCode = "MBP";
    }
    
    public SageOfTheInwardEye(final SageOfTheInwardEye card) {
        super(card);
    }
    
    @Override
    public SageOfTheInwardEye copy() {
        return new SageOfTheInwardEye(this);
    }
}
