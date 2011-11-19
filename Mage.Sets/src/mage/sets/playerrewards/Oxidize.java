package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Oxidize extends mage.sets.darksteel.Oxidize {
    public Oxidize(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 7;
        this.expansionSetCode = "MPR";
    }

    public Oxidize(final Oxidize card) {
        super(card);
    }

    @Override
    public Oxidize copy() {
        return new Oxidize(this);
    }
}
