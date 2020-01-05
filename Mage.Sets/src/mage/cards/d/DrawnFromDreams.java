package mage.cards.d;

import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrawnFromDreams extends CardImpl {

    public DrawnFromDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // Look at the top seven cards of your library. Put two of them into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                StaticValue.get(7), false, StaticValue.get(2),
                StaticFilters.FILTER_CARD, Zone.LIBRARY, false, false
        ).setBackInRandomOrder(true));
    }

    private DrawnFromDreams(final DrawnFromDreams card) {
        super(card);
    }

    @Override
    public DrawnFromDreams copy() {
        return new DrawnFromDreams(this);
    }
}
