package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class FlameJavelin extends mage.sets.shadowmoor.FlameJavelin {
    public FlameJavelin(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 32;
        this.expansionSetCode = "MPR";
    }

    public FlameJavelin(final FlameJavelin card) {
        super(card);
    }

    @Override
    public FlameJavelin copy() {
        return new FlameJavelin(this);
    }
}
