package mage.sets.mediainserts;

import java.util.UUID;

public class StewardOfValeron extends mage.sets.shardsofalara.StewardOfValeron {
    
    public StewardOfValeron(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "21";
        this.expansionSetCode = "MBP";
    }
    
    public StewardOfValeron(final StewardOfValeron card) {
        super(card);
    }
    
    @Override
    public StewardOfValeron copy() {
        return new StewardOfValeron(this);
    }
}
