package mage.sets.mediainserts;

import java.util.UUID;

public class IvorytuskFortress extends mage.sets.khansoftarkir.IvorytuskFortress {
    
    public IvorytuskFortress(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "95";
        this.expansionSetCode = "MBP";
    }
    
    public IvorytuskFortress(final IvorytuskFortress card) {
        super(card);
    }
    
    @Override
    public IvorytuskFortress copy() {
        return new IvorytuskFortress(this);
    }
}
