package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Negate extends mage.sets.magic2010.Negate {
    public Negate(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 38;
        this.expansionSetCode = "MPR";
    }

    public Negate(final Negate card) {
        super(card);
    }

    @Override
    public Negate copy() {
        return new Negate(this);
    }
}
