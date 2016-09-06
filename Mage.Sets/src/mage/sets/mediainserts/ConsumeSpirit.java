package mage.sets.mediainserts;

import java.util.UUID;

public class ConsumeSpirit extends mage.sets.planechase.ConsumeSpirit {
    
    public ConsumeSpirit(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "54";
        this.expansionSetCode = "MBP";
    }
    
    public ConsumeSpirit(final ConsumeSpirit card) {
        super(card);
    }
    
    @Override
    public ConsumeSpirit copy() {
        return new ConsumeSpirit(this);
    }
}
