package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Putrefy extends mage.sets.ravnika.Putrefy {
    public Putrefy(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 14;
        this.expansionSetCode = "MPR";
    }

    public Putrefy(final Putrefy card) {
        super(card);
    }

    @Override
    public Putrefy copy() {
        return new Putrefy(this);
    }
}
