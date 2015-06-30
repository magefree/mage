package mage.sets.mediainserts;

import java.util.UUID;

public class HighTide extends mage.sets.fallenempires.HighTide1 {

    public HighTide(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 80;
        this.expansionSetCode = "MBP";
    }

    public HighTide(final HighTide card) {
        super(card);
    }

    @Override
    public HighTide copy() {
        return new HighTide(this);
    }
}
