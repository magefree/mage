package mage.cards.l;

import java.util.UUID;

import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class LavaglidePathway extends CardImpl {

    public LavaglidePathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.nightCard = true;
        this.transformable = true;

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
