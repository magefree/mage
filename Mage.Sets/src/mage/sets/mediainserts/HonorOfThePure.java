package mage.sets.mediainserts;

import java.util.UUID;

public class HonorOfThePure extends mage.sets.magic2010.HonorOfThePure {
    
    public HonorOfThePure(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "20";
        this.expansionSetCode = "MBP";
    }
    
    public HonorOfThePure(final HonorOfThePure card) {
        super(card);
    }
    
    @Override
    public HonorOfThePure copy() {
        return new HonorOfThePure(this);
    }
}
