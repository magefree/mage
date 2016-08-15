package mage.sets.mediainserts;

import java.util.UUID;

public class SoulOfRavnica extends mage.sets.magic2015.SoulOfRavnica {
    
    public SoulOfRavnica(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "87";
        this.expansionSetCode = "MBP";
    }
    
    public SoulOfRavnica(final SoulOfRavnica card) {
        super(card);
    }
    
    @Override
    public SoulOfRavnica copy() {
        return new SoulOfRavnica(this);
    }
}
