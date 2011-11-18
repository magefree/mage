package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Unmake extends mage.sets.eventide.Unmake {
    public Unmake(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 33;
        this.expansionSetCode = "MPR";
    }

    public Unmake(final Unmake card) {
        super(card);
    }

    @Override
    public Unmake copy() {
        return new Unmake(this);
    }
}
