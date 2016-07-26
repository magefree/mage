package mage.sets.mediainserts;

import java.util.UUID;

public class SerraAvatar extends mage.sets.magic2013.SerraAvatar {
    
    public SerraAvatar(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "48";
        this.expansionSetCode = "MBP";
    }
    
    public SerraAvatar(final SerraAvatar card) {
        super(card);
    }
    
    @Override
    public SerraAvatar copy() {
        return new SerraAvatar(this);
    }
}
