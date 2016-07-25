package mage.sets.mediainserts;

import java.util.UUID;

public class BlueElementalBlast extends mage.sets.limitedalpha.BlueElementalBlast {
    
    public BlueElementalBlast(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "5";
        this.expansionSetCode = "MBP";
    }
    
    public BlueElementalBlast(final BlueElementalBlast card) {
        super(card);
    }
    
    @Override
    public BlueElementalBlast copy() {
        return new BlueElementalBlast(this);
    }
}
