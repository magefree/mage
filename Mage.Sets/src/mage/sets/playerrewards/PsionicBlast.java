package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class PsionicBlast extends mage.sets.timeshifted.PsionicBlast {
    public PsionicBlast(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 20;
        this.expansionSetCode = "MPR";
    }

    public PsionicBlast(final PsionicBlast card) {
        super(card);
    }

    @Override
    public PsionicBlast copy() {
        return new PsionicBlast(this);
    }
}
