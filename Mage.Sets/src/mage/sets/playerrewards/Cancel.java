package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Cancel extends mage.sets.tenth.Cancel {
    public Cancel(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 41;
        this.expansionSetCode = "MPR";
    }

    public Cancel(final Cancel card) {
        super(card);
    }

    @Override
    public Cancel copy() {
        return new Cancel(this);
    }
}
