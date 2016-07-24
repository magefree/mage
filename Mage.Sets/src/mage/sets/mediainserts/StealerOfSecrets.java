package mage.sets.mediainserts;

import java.util.UUID;

public class StealerOfSecrets extends mage.sets.returntoravnica.StealerOfSecrets {
    
    public StealerOfSecrets(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "89";
        this.expansionSetCode = "MBP";
    }
    
    public StealerOfSecrets(final StealerOfSecrets card) {
        super(card);
    }
    
    @Override
    public StealerOfSecrets copy() {
        return new StealerOfSecrets(this);
    }
}
