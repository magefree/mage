package mage.sets.mediainserts;

import java.util.UUID;

public class OgreBattledriver extends mage.sets.magic2014.OgreBattledriver {
    
    public OgreBattledriver(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "69";
        this.expansionSetCode = "MBP";
    }
    
    public OgreBattledriver(final OgreBattledriver card) {
        super(card);
    }
    
    @Override
    public OgreBattledriver copy() {
        return new OgreBattledriver(this);
    }
}
