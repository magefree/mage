package mage.sets.mediainserts;

import java.util.UUID;

public class RakshasaVizier extends mage.sets.khansoftarkir.RakshasaVizier {
    
    public RakshasaVizier(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "96";
        this.expansionSetCode = "MBP";
    }
    
    public RakshasaVizier(final RakshasaVizier card) {
        super(card);
    }
    
    @Override
    public RakshasaVizier copy() {
        return new RakshasaVizier(this);
    }
}
