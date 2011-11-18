package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Infest extends mage.sets.shardsofalara.Infest {
    public Infest(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 43;
        this.expansionSetCode = "MPR";
    }

    public Infest(final Infest card) {
        super(card);
    }

    @Override
    public Infest copy() {
        return new Infest(this);
    }
}
