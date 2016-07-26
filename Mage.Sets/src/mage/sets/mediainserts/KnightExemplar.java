package mage.sets.mediainserts;

import java.util.UUID;

public class KnightExemplar extends mage.sets.magic2011.KnightExemplar {
    
    public KnightExemplar(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "46";
        this.expansionSetCode = "MBP";
    }
    
    public KnightExemplar(final KnightExemplar card) {
        super(card);
    }
    
    @Override
    public KnightExemplar copy() {
        return new KnightExemplar(this);
    }
}
