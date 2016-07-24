package mage.sets.mediainserts;

import java.util.UUID;

public class LilianaOfTheDarkRealms extends mage.sets.magic2014.LilianaOfTheDarkRealms {
    
    public LilianaOfTheDarkRealms(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "74";
        this.expansionSetCode = "MBP";
    }
    
    public LilianaOfTheDarkRealms(final LilianaOfTheDarkRealms card) {
        super(card);
    }
    
    @Override
    public LilianaOfTheDarkRealms copy() {
        return new LilianaOfTheDarkRealms(this);
    }
}
