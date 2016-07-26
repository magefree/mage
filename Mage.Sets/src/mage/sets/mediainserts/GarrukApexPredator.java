package mage.sets.mediainserts;

import java.util.UUID;

public class GarrukApexPredator extends mage.sets.magic2015.GarrukApexPredator {
    
    public GarrukApexPredator(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "104";
        this.expansionSetCode = "MBP";
    }
    
    public GarrukApexPredator(final GarrukApexPredator card) {
        super(card);
    }
    
    @Override
    public GarrukApexPredator copy() {
        return new GarrukApexPredator(this);
    }
}
