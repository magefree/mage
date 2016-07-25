package mage.sets.mediainserts;

import java.util.UUID;

public class JaceBeleren extends mage.sets.lorwyn.JaceBeleren {
    
    public JaceBeleren(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "15";
        this.expansionSetCode = "MBP";
    }
    
    public JaceBeleren(final JaceBeleren card) {
        super(card);
    }
    
    @Override
    public JaceBeleren copy() {
        return new JaceBeleren(this);
    }
}
