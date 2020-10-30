package mage.cards.l;

import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LavaglidePathway extends CardImpl {

    public LavaglidePathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private LavaglidePathway(final LavaglidePathway card) {
        super(card);
    }

    @Override
    public LavaglidePathway copy() {
        return new LavaglidePathway(this);
    }
}
