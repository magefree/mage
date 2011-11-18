package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class ManaLeak extends mage.sets.magic2011.ManaLeak {
    public ManaLeak(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 8;
        this.expansionSetCode = "MPR";
    }

    public ManaLeak(final ManaLeak card) {
        super(card);
    }

    @Override
    public ManaLeak copy() {
        return new ManaLeak(this);
    }
}
