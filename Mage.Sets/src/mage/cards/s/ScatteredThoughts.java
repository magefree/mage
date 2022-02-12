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
public final class ScatteredThoughts extends CardImpl {

    public ScatteredThoughts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Look at the top four cards of your library. Put two of those cards into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                StaticValue.get(4), false, StaticValue.get(2),
                StaticFilters.FILTER_CARD, Zone.LIBRARY, false, false
        ));
    }

    private ScatteredThoughts(final ScatteredThoughts card) {
        super(card);
    }

    @Override
    public ScatteredThoughts copy() {
        return new ScatteredThoughts(this);
    }
}
