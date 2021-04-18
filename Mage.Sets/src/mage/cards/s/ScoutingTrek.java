
package mage.cards.s;

import mage.abilities.effects.common.RecruiterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class ScoutingTrek extends CardImpl {

    public ScoutingTrek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Search your library for any number of basic land cards. Reveal those cards, then shuffle your library and put them on top of it.
        this.getSpellAbility().addEffect(new RecruiterEffect(StaticFilters.FILTER_CARD_BASIC_LANDS).setText("search your library for any number of basic land cards, reveal those cards, then shuffle and put them on top"));
    }

    private ScoutingTrek(final ScoutingTrek card) {
        super(card);
    }

    @Override
    public ScoutingTrek copy() {
        return new ScoutingTrek(this);
    }
}
