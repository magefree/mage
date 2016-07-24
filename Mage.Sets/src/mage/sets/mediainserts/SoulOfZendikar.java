package mage.sets.mediainserts;

import java.util.UUID;

public class SoulOfZendikar extends mage.sets.magic2015.SoulOfZendikar {
    
    public SoulOfZendikar(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "88";
        this.expansionSetCode = "MBP";
    }
    
    public SoulOfZendikar(final SoulOfZendikar card) {
        super(card);
    }
    
    @Override
    public SoulOfZendikar copy() {
        return new SoulOfZendikar(this);
    }
}
