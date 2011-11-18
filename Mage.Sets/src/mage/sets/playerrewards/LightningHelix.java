package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class LightningHelix extends mage.sets.ravnika.LightningHelix {
    public LightningHelix(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 16;
        this.expansionSetCode = "MPR";
    }

    public LightningHelix(final LightningHelix card) {
        super(card);
    }

    @Override
    public LightningHelix copy() {
        return new LightningHelix(this);
    }
}
