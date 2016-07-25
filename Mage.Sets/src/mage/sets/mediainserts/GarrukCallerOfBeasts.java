package mage.sets.mediainserts;

import java.util.UUID;

public class GarrukCallerOfBeasts extends mage.sets.magic2014.GarrukCallerOfBeasts {
    
    public GarrukCallerOfBeasts(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "76";
        this.expansionSetCode = "MBP";
    }
    
    public GarrukCallerOfBeasts(final GarrukCallerOfBeasts card) {
        super(card);
    }
    
    @Override
    public GarrukCallerOfBeasts copy() {
        return new GarrukCallerOfBeasts(this);
    }
}
