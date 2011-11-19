package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Fireball extends mage.sets.magic2010.Fireball {
    public Fireball(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 6;
        this.expansionSetCode = "MPR";
    }

    public Fireball(final Fireball card) {
        super(card);
    }

    @Override
    public Fireball copy() {
        return new Fireball(this);
    }
}
