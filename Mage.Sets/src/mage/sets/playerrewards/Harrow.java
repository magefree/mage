package mage.sets.playerrewards;

import java.util.UUID;

public class Harrow extends mage.sets.zendikar.Harrow {
    public Harrow(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 48;
        this.expansionSetCode = "MPR";
    }

    public Harrow(final Harrow card) {
        super(card);
    }

    @Override
    public Harrow copy() {
        return new Harrow(this);
    }
}
