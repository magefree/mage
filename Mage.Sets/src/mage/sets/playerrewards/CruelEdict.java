package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class CruelEdict extends mage.sets.tenth.CruelEdict {
    public CruelEdict(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 21;
        this.expansionSetCode = "MPR";
    }

    public CruelEdict(final CruelEdict card) {
        super(card);
    }

    @Override
    public CruelEdict copy() {
        return new CruelEdict(this);
    }
}
