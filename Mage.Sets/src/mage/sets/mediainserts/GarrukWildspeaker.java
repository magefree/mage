package mage.sets.mediainserts;

import java.util.UUID;

public class GarrukWildspeaker extends mage.sets.lorwyn.GarrukWildspeaker {
    
    public GarrukWildspeaker(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "16";
        this.expansionSetCode = "MBP";
    }
    
    public GarrukWildspeaker(final GarrukWildspeaker card) {
        super(card);
    }
    
    @Override
    public GarrukWildspeaker copy() {
        return new GarrukWildspeaker(this);
    }
}
