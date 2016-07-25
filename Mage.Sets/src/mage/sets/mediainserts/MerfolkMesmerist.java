package mage.sets.mediainserts;

import java.util.UUID;

public class MerfolkMesmerist extends mage.sets.magic2012.MerfolkMesmerist {
    
    public MerfolkMesmerist(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "45";
        this.expansionSetCode = "MBP";
    }
    
    public MerfolkMesmerist(final MerfolkMesmerist card) {
        super(card);
    }
    
    @Override
    public MerfolkMesmerist copy() {
        return new MerfolkMesmerist(this);
    }
}
