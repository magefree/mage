package mage.sets.playerrewards;

import java.util.UUID;

public class CelestialPurge extends mage.sets.conflux.CelestialPurge {
    public CelestialPurge(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 45;
        this.expansionSetCode = "MPR";
    }

    public CelestialPurge(final CelestialPurge card) {
        super(card);
    }

    @Override
    public CelestialPurge copy() {
        return new CelestialPurge(this);
    }
}
