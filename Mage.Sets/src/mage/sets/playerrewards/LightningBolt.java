package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class LightningBolt extends mage.sets.magic2010.LightningBolt {
    public LightningBolt(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 40;
        this.expansionSetCode = "MPR";
    }

    public LightningBolt(final LightningBolt card) {
        super(card);
    }

    @Override
    public LightningBolt copy() {
        return new LightningBolt(this);
    }
}
