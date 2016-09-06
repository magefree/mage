package mage.sets.mediainserts;

import java.util.UUID;

public class Electrolyze extends mage.sets.guildpact.Electrolyze {
    
    public Electrolyze(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "42";
        this.expansionSetCode = "MBP";
    }
    
    public Electrolyze(final Electrolyze card) {
        super(card);
    }
    
    @Override
    public Electrolyze copy() {
        return new Electrolyze(this);
    }
}
