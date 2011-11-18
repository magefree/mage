package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Terminate extends mage.sets.alarareborn.Terminate {
    public Terminate(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 39;
        this.expansionSetCode = "MPR";
    }

    public Terminate(final Terminate card) {
        super(card);
    }

    @Override
    public Terminate copy() {
        return new Terminate(this);
    }
}
