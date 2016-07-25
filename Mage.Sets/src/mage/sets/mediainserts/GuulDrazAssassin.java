package mage.sets.mediainserts;

import java.util.UUID;

public class GuulDrazAssassin extends mage.sets.riseoftheeldrazi.GuulDrazAssassin {
    
    public GuulDrazAssassin(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "26";
        this.expansionSetCode = "MBP";
    }
    
    public GuulDrazAssassin(final GuulDrazAssassin card) {
        super(card);
    }
    
    @Override
    public GuulDrazAssassin copy() {
        return new GuulDrazAssassin(this);
    }
}
