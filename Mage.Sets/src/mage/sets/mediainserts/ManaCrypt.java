package mage.sets.mediainserts;

import java.util.UUID;

public class ManaCrypt extends mage.sets.judgepromo.ManaCrypt {
    
    public ManaCrypt(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "6";
        this.expansionSetCode = "MBP";
    }
    
    public ManaCrypt(final ManaCrypt card) {
        super(card);
    }
    
    @Override
    public ManaCrypt copy() {
        return new ManaCrypt(this);
    }
}
