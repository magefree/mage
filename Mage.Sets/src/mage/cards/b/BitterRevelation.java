
package mage.cards.b;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author emerald000
 */
public final class BitterRevelation extends CardImpl {

    public BitterRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Look at the top four cards of your library. Put two of them into your hand and the rest into your graveyard. You lose 2 life.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(StaticValue.get(4), false, StaticValue.get(2),
                StaticFilters.FILTER_CARD, Zone.GRAVEYARD, false, false, false, Zone.HAND, false));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
    }

    private BitterRevelation(final BitterRevelation card) {
        super(card);
    }

    @Override
    public BitterRevelation copy() {
        return new BitterRevelation(this);
    }
}
