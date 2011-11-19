package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Terror extends mage.sets.tenth.Terror {
    public Terror(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 5;
        this.expansionSetCode = "MPR";
    }

    public Terror(final Terror card) {
        super(card);
    }

    @Override
    public Terror copy() {
        return new Terror(this);
    }
}
