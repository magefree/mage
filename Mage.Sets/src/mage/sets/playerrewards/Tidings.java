package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Tidings extends mage.sets.tenth.Tidings {
    public Tidings(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 25;
        this.expansionSetCode = "MPR";
    }

    public Tidings(final Tidings card) {
        super(card);
    }

    @Override
    public Tidings copy() {
        return new Tidings(this);
    }
}
