package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Pyroclasm extends mage.sets.tenth.Pyroclasm {
    public Pyroclasm(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 12;
        this.expansionSetCode = "MPR";
    }

    public Pyroclasm(final Pyroclasm card) {
        super(card);
    }

    @Override
    public Pyroclasm copy() {
        return new Pyroclasm(this);
    }
}
