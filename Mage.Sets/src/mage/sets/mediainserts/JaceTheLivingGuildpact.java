package mage.sets.mediainserts;

import java.util.UUID;

public class JaceTheLivingGuildpact extends mage.sets.magic2015.JaceTheLivingGuildpact {
    
    public JaceTheLivingGuildpact(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "100";
        this.expansionSetCode = "MBP";
    }
    
    public JaceTheLivingGuildpact(final JaceTheLivingGuildpact card) {
        super(card);
    }
    
    @Override
    public JaceTheLivingGuildpact copy() {
        return new JaceTheLivingGuildpact(this);
    }
}
