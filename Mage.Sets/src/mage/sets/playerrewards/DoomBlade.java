package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class DoomBlade extends mage.sets.magic2010.DoomBlade {
    public DoomBlade(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 51;
        this.expansionSetCode = "MPR";
    }

    public DoomBlade(final DoomBlade card) {
        super(card);
    }

    @Override
    public DoomBlade copy() {
        return new DoomBlade(this);
    }
}
