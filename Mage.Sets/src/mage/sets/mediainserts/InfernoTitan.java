package mage.sets.mediainserts;

import java.util.UUID;

public class InfernoTitan extends mage.sets.magic2011.InfernoTitan {
    
    public InfernoTitan(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "36";
        this.expansionSetCode = "MBP";
    }
    
    public InfernoTitan(final InfernoTitan card) {
        super(card);
    }
    
    @Override
    public InfernoTitan copy() {
        return new InfernoTitan(this);
    }
}
