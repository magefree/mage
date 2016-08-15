package mage.sets.mediainserts;

import java.util.UUID;

public class KarametrasAcolyte extends mage.sets.theros.KarametrasAcolyte {
    
    public KarametrasAcolyte(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "78";
        this.expansionSetCode = "MBP";
    }
    
    public KarametrasAcolyte(final KarametrasAcolyte card) {
        super(card);
    }
    
    @Override
    public KarametrasAcolyte copy() {
        return new KarametrasAcolyte(this);
    }
}
