package mage.sets.playerrewards;

import java.util.UUID;

public class BurstLightning extends mage.sets.zendikar.BurstLightning {
    public BurstLightning(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 47;
        this.expansionSetCode = "MPR";
    }

    public BurstLightning(final BurstLightning card) {
        super(card);
    }

    @Override
    public BurstLightning copy() {
        return new BurstLightning(this);
    }
}
