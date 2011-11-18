package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Corrupt extends mage.sets.magic2011.Corrupt {
    public Corrupt(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 30;
        this.expansionSetCode = "MPR";
    }

    public Corrupt(final Corrupt card) {
        super(card);
    }

    @Override
    public Corrupt copy() {
        return new Corrupt(this);
    }
}
