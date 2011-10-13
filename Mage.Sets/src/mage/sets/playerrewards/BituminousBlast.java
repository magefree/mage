package mage.sets.playerrewards;

import java.util.UUID;

public class BituminousBlast extends mage.sets.alarareborn.BituminousBlast {
    public BituminousBlast(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 46;
        this.expansionSetCode = "MPR";
    }

    public BituminousBlast(final BituminousBlast card) {
        super(card);
    }

    @Override
    public BituminousBlast copy() {
        return new BituminousBlast(this);
    }
}
