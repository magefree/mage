package mage.sets.mediainserts;

import java.util.UUID;

public class Fireball extends mage.sets.limitedalpha.Fireball {
    
    public Fireball(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "4";
        this.expansionSetCode = "MBP";
    }
    
    public Fireball(final Fireball card) {
        super(card);
    }
    
    @Override
    public Fireball copy() {
        return new Fireball(this);
    }
}
