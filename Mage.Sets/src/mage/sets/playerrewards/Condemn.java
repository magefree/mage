package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Condemn extends mage.sets.tenth.Condemn {
    public Condemn(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 18;
        this.expansionSetCode = "MPR";
    }

    public Condemn(final Condemn card) {
        super(card);
    }

    @Override
    public Condemn copy() {
        return new Condemn(this);
    }
}
