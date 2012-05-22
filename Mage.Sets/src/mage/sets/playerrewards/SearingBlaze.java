package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class SearingBlaze extends mage.sets.worldwake.SearingBlaze {
    public SearingBlaze(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 53;
        this.expansionSetCode = "MPR";
    }

    public SearingBlaze(final SearingBlaze card) {
        super(card);
    }

    @Override
    public SearingBlaze copy() {
        return new SearingBlaze(this);
    }
}
