package mage.sets.mediainserts;

import java.util.UUID;

public class CathedralOfWar extends mage.sets.magic2013.CathedralOfWar {
    
    public CathedralOfWar(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "51";
        this.expansionSetCode = "MBP";
    }
    
    public CathedralOfWar(final CathedralOfWar card) {
        super(card);
    }
    
    @Override
    public CathedralOfWar copy() {
        return new CathedralOfWar(this);
    }
}
