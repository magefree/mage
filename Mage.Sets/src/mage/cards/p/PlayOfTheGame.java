
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.keyword.AssistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class PlayOfTheGame extends CardImpl {

    public PlayOfTheGame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{W}{W}");

        // Assist
        this.addAbility(new AssistAbility());

        // Exile all nonland permanents.
        this.getSpellAbility().addEffect(new ExileAllEffect(StaticFilters.FILTER_PERMANENTS_NON_LAND));
    }

    private PlayOfTheGame(final PlayOfTheGame card) {
        super(card);
    }

    @Override
    public PlayOfTheGame copy() {
        return new PlayOfTheGame(this);
    }
}
