package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class RemoveSoul extends mage.sets.tenth.RemoveSoul {
    public RemoveSoul(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 35;
        this.expansionSetCode = "MPR";
    }

    public RemoveSoul(final RemoveSoul card) {
        super(card);
    }

    @Override
    public RemoveSoul copy() {
        return new RemoveSoul(this);
    }
}
