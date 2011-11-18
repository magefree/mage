package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Mortify extends mage.sets.guildpact.Mortify{
    public Mortify(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 19;
        this.expansionSetCode = "MPR";
    }

    public Mortify(final Mortify card) {
        super(card);
    }

    @Override
    public Mortify copy() {
        return new Mortify(this);
    }
}
