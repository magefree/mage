package mage.cards.o;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OtherworldlyGaze extends CardImpl {

    public OtherworldlyGaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Look at the top three cards of your library. Put any number of them into your graveyard and the rest back on top of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                StaticValue.get(3), false, StaticValue.get(5), StaticFilters.FILTER_CARD_CARDS,
                Zone.LIBRARY, true, false, true, Zone.GRAVEYARD, false
        ));

        // Flashback {1}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{U}")));
    }

    private OtherworldlyGaze(final OtherworldlyGaze card) {
        super(card);
    }

    @Override
    public OtherworldlyGaze copy() {
        return new OtherworldlyGaze(this);
    }
}
