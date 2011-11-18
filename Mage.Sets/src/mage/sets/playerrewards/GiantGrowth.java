package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class GiantGrowth extends mage.sets.tenth.GiantGrowth {
    public GiantGrowth(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 13;
        this.expansionSetCode = "MPR";
    }

    public GiantGrowth(final GiantGrowth card) {
        super(card);
    }

    @Override
    public GiantGrowth copy() {
        return new GiantGrowth(this);
    }
}
