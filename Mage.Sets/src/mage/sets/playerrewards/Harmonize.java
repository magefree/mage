package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Harmonize extends mage.sets.planarchaos.Harmonize {
    public Harmonize(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 28;
        this.expansionSetCode = "MPR";
    }

    public Harmonize(final Harmonize card) {
        super(card);
    }

    @Override
    public Harmonize copy() {
        return new Harmonize(this);
    }
}
