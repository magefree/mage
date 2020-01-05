package mage.cards.s;

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
public final class ShimmerOfPossibility extends CardImpl {

    public ShimmerOfPossibility(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                StaticValue.get(4), false, StaticValue.get(1), StaticFilters.FILTER_CARD, Zone.LIBRARY,
                false, false, false, Zone.HAND, false, false, false
        ).setBackInRandomOrder(true));
    }

    private ShimmerOfPossibility(final ShimmerOfPossibility card) {
        super(card);
    }

    @Override
    public ShimmerOfPossibility copy() {
        return new ShimmerOfPossibility(this);
    }
}
