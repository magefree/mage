package mage.sets.mediainserts;

import java.util.UUID;

public class FaithlessLooting extends mage.sets.darkascension.FaithlessLooting {
    
    public FaithlessLooting(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "39";
        this.expansionSetCode = "MBP";
    }
    
    public FaithlessLooting(final FaithlessLooting card) {
        super(card);
    }
    
    @Override
    public FaithlessLooting copy() {
        return new FaithlessLooting(this);
    }
}
