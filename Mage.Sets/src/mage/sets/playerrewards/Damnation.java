package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Damnation extends mage.sets.planarchaos.Damnation{
    public Damnation(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 24;
        this.expansionSetCode = "MPR";
    }

    public Damnation(final Damnation card) {
        super(card);
    }

    @Override
    public Damnation copy() {
        return new Damnation(this);
    }
}
