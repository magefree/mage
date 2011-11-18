package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Ponder extends mage.sets.magic2010.Ponder {
    public Ponder(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 29;
        this.expansionSetCode = "MPR";
    }

    public Ponder(final Ponder card) {
        super(card);
    }

    @Override
    public Ponder copy() {
        return new Ponder(this);
    }
}
