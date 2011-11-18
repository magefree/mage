package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Incinerate extends mage.sets.tenth.Incinerate {
    public Incinerate(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 26;
        this.expansionSetCode = "MPR";
    }

    public Incinerate(final Incinerate card) {
        super(card);
    }

    @Override
    public Incinerate copy() {
        return new Incinerate(this);
    }
}
