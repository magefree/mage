package mage.sets.mediainserts;

import java.util.UUID;

public class ScavengingOoze extends mage.sets.commander.ScavengingOoze {
    
    public ScavengingOoze(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "70";
        this.expansionSetCode = "MBP";
    }
    
    public ScavengingOoze(final ScavengingOoze card) {
        super(card);
    }
    
    @Override
    public ScavengingOoze copy() {
        return new ScavengingOoze(this);
    }
}
