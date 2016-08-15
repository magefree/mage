package mage.sets.mediainserts;

import java.util.UUID;

public class MirranCrusader extends mage.sets.mirrodinbesieged.MirranCrusader {
    
    public MirranCrusader(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "32";
        this.expansionSetCode = "MBP";
    }
    
    public MirranCrusader(final MirranCrusader card) {
        super(card);
    }
    
    @Override
    public MirranCrusader copy() {
        return new MirranCrusader(this);
    }
}
