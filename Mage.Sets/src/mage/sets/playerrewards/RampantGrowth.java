package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class RampantGrowth extends mage.sets.magic2010.RampantGrowth {
    public RampantGrowth(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 37;
        this.expansionSetCode = "MPR";
    }

    public RampantGrowth(final RampantGrowth card) {
        super(card);
    }

    @Override
    public RampantGrowth copy() {
        return new RampantGrowth(this);
    }
}
