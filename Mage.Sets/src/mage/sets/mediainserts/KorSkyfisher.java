package mage.sets.mediainserts;

import java.util.UUID;

public class KorSkyfisher extends mage.sets.zendikar.KorSkyfisher {
    
    public KorSkyfisher(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "25";
        this.expansionSetCode = "MBP";
    }
    
    public KorSkyfisher(final KorSkyfisher card) {
        super(card);
    }
    
    @Override
    public KorSkyfisher copy() {
        return new KorSkyfisher(this);
    }
}
