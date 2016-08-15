package mage.sets.mediainserts;

import java.util.UUID;

public class VampireNocturnus extends mage.sets.magic2010.VampireNocturnus {
    
    public VampireNocturnus(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "50";
        this.expansionSetCode = "MBP";
    }
    
    public VampireNocturnus(final VampireNocturnus card) {
        super(card);
    }
    
    @Override
    public VampireNocturnus copy() {
        return new VampireNocturnus(this);
    }
}
