package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Recollect extends mage.sets.tenth.Recollect {
    public Recollect(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 23;
        this.expansionSetCode = "MPR";
    }

    public Recollect(final Recollect card) {
        super(card);
    }

    @Override
    public Recollect copy() {
        return new Recollect(this);
    }
}
