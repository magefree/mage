package mage.sets.vintagemasters;

import java.util.UUID;

/**
 * @author Laxika
 */
public class UrborgUprising extends mage.sets.apocalypse.UrborgUprising {

    public UrborgUprising(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "144";
        this.expansionSetCode = "VMA";
    }

    public UrborgUprising(final UrborgUprising card) {
        super(card);
    }

    @Override
    public UrborgUprising copy() {
        return new UrborgUprising(this);
    }
}
