package mage.sets.mediainserts;

import java.util.UUID;

public class GoblinRabblemaster extends mage.sets.magic2015.GoblinRabblemaster {
    
    public GoblinRabblemaster(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "98";
        this.expansionSetCode = "MBP";
    }
    
    public GoblinRabblemaster(final GoblinRabblemaster card) {
        super(card);
    }
    
    @Override
    public GoblinRabblemaster copy() {
        return new GoblinRabblemaster(this);
    }
}
