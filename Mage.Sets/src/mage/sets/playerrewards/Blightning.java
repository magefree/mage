package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class Blightning extends mage.sets.shardsofalara.Blightning {
    public Blightning(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 36;
        this.expansionSetCode = "MPR";
    }

    public Blightning(final Blightning card) {
        super(card);
    }

    @Override
    public Blightning copy() {
        return new Blightning(this);
    }
}
