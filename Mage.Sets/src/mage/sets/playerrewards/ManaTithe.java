package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class ManaTithe extends mage.sets.planarchaos.ManaTithe {
    public ManaTithe(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 27;
        this.expansionSetCode = "MPR";
    }

    public ManaTithe(final ManaTithe card) {
        super(card);
    }

    @Override
    public ManaTithe copy() {
        return new ManaTithe(this);
    }
}
