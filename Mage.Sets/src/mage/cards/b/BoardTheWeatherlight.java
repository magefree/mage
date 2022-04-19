package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.HistoricPredicate;

/**
 *
 * @author TheElk801
 */
public final class BoardTheWeatherlight extends CardImpl {

    private static final FilterCard filter = new FilterCard("a historic card");

    static {
        filter.add(HistoricPredicate.instance);
    }

    public BoardTheWeatherlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Look at the top five cards of your library. You may reveal a historic card from among them and put it into your hand. Put the rest on the bottom of your library in random order.
        this.getSpellAbility().addEffect(
                new LookLibraryAndPickControllerEffect(5, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM)
                        .setText("Look at the top five cards of your library. You may reveal a historic card from among them"
                                + " and put it into your hand. Put the rest on the bottom of your library in a random order. "
                                + "<i>(Artifacts, legendaries, and Sagas are historic.)</i>")
        );
    }

    private BoardTheWeatherlight(final BoardTheWeatherlight card) {
        super(card);
    }

    @Override
    public BoardTheWeatherlight copy() {
        return new BoardTheWeatherlight(this);
    }
}
