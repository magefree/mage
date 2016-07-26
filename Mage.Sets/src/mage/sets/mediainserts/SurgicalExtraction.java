package mage.sets.mediainserts;

import java.util.UUID;

public class SurgicalExtraction extends mage.sets.newphyrexia.SurgicalExtraction {
    
    public SurgicalExtraction(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "33";
        this.expansionSetCode = "MBP";
    }
    
    public SurgicalExtraction(final SurgicalExtraction card) {
        super(card);
    }
    
    @Override
    public SurgicalExtraction copy() {
        return new SurgicalExtraction(this);
    }
}
