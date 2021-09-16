
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author North
 */
public final class ForbiddenAlchemy extends CardImpl {

    public ForbiddenAlchemy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Look at the top four cards of your library. Put one of them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(StaticValue.get(4), false, StaticValue.get(1),
                StaticFilters.FILTER_CARD, Zone.GRAVEYARD, false, false, false, Zone.HAND, false));

        // Flashback {6}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{6}{B}")));
    }

    private ForbiddenAlchemy(final ForbiddenAlchemy card) {
        super(card);
    }

    @Override
    public ForbiddenAlchemy copy() {
        return new ForbiddenAlchemy(this);
    }
}
