package mage.sets.mediainserts;

import java.util.UUID;

public class AngelicSkirmisher extends mage.sets.gatecrash.AngelicSkirmisher {
    
    public AngelicSkirmisher(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "90";
        this.expansionSetCode = "MBP";
    }
    
    public AngelicSkirmisher(final AngelicSkirmisher card) {
        super(card);
    }
    
    @Override
    public AngelicSkirmisher copy() {
        return new AngelicSkirmisher(this);
    }
}
