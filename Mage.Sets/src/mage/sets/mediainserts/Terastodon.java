package mage.sets.mediainserts;

import java.util.UUID;

public class Terastodon extends mage.sets.worldwake.Terastodon {
    
    public Terastodon(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "52";
        this.expansionSetCode = "MBP";
    }
    
    public Terastodon(final Terastodon card) {
        super(card);
    }
    
    @Override
    public Terastodon copy() {
        return new Terastodon(this);
    }
}
