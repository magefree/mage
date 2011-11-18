package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Disenchant extends mage.sets.tempest.Disenchant {
    public Disenchant(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 22;
        this.expansionSetCode = "MPR";
    }

    public Disenchant(final Disenchant card) {
        super(card);
    }

    @Override
    public Disenchant copy() {
        return new Disenchant(this);
    }
}
