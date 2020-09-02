package mage.cards.t;

import java.util.UUID;

import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class TimbercrownPathway extends CardImpl {

    public TimbercrownPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.nightCard = true;

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private TimbercrownPathway(final TimbercrownPathway card) {
        super(card);
    }

    @Override
    public TimbercrownPathway copy() {
        return new TimbercrownPathway(this);
    }
}
