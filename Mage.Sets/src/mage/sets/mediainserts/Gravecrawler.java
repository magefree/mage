package mage.sets.mediainserts;

import java.util.UUID;

public class Gravecrawler extends mage.sets.darkascension.Gravecrawler {
    
    public Gravecrawler(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "41";
        this.expansionSetCode = "MBP";
    }
    
    public Gravecrawler(final Gravecrawler card) {
        super(card);
    }
    
    @Override
    public Gravecrawler copy() {
        return new Gravecrawler(this);
    }
}
