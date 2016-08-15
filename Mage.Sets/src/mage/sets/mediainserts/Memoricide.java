package mage.sets.mediainserts;

import java.util.UUID;

public class Memoricide extends mage.sets.scarsofmirrodin.Memoricide {
    
    public Memoricide(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "29";
        this.expansionSetCode = "MBP";
    }
    
    public Memoricide(final Memoricide card) {
        super(card);
    }
    
    @Override
    public Memoricide copy() {
        return new Memoricide(this);
    }
}
