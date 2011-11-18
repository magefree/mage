package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class WrathOfGod extends mage.sets.tenth.WrathOfGod{
    public WrathOfGod(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 17;
        this.expansionSetCode = "MPR";
    }

    public WrathOfGod(final WrathOfGod card) {
        super(card);
    }

    @Override
    public WrathOfGod copy() {
        return new WrathOfGod(this);
    }
}
