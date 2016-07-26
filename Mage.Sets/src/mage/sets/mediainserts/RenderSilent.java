package mage.sets.mediainserts;

import java.util.UUID;

public class RenderSilent extends mage.sets.dragonsmaze.RenderSilent {
    
    public RenderSilent(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "66";
        this.expansionSetCode = "MBP";
    }
    
    public RenderSilent(final RenderSilent card) {
        super(card);
    }
    
    @Override
    public RenderSilent copy() {
        return new RenderSilent(this);
    }
}
