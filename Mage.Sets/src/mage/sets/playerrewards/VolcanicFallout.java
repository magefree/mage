package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class VolcanicFallout extends mage.sets.conflux.VolcanicFallout {
    public VolcanicFallout(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 44;
        this.expansionSetCode = "MPR";
    }

    public VolcanicFallout(final VolcanicFallout card) {
        super(card);
    }

    @Override
    public VolcanicFallout copy() {
        return new VolcanicFallout(this);
    }
}
