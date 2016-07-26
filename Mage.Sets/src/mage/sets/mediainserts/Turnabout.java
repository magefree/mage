package mage.sets.mediainserts;

import java.util.UUID;

public class Turnabout extends mage.sets.urzassaga.Turnabout {
    
    public Turnabout(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "60";
        this.expansionSetCode = "MBP";
    }
    
    public Turnabout(final Turnabout card) {
        super(card);
    }
    
    @Override
    public Turnabout copy() {
        return new Turnabout(this);
    }
}
