package mage.sets.mediainserts;

import java.util.UUID;

public class AnkleShanker extends mage.sets.khansoftarkir.AnkleShanker {
    
    public AnkleShanker(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "93";
        this.expansionSetCode = "MBP";
    }
    
    public AnkleShanker(final AnkleShanker card) {
        super(card);
    }
    
    @Override
    public AnkleShanker copy() {
        return new AnkleShanker(this);
    }
}
