
package mage.cards.m;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author North
 */
public final class MitoticManipulation extends CardImpl {

    private static final FilterCard filter = new FilterCard("a card with the same name as a permanent");

    static {
        filter.add(MitoticManipulationPredicate.instance);
    }

    public MitoticManipulation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{U}");

        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                7, 1, filter, PutCards.BATTLEFIELD, PutCards.BOTTOM_ANY
        ).setText("look at the top seven cards of your library. You may put one of those cards onto the battlefield " +
                "if it has the same name as a permanent. Put the rest on the bottom of your library in any order"));
    }

    private MitoticManipulation(final MitoticManipulation card) {
        super(card);
    }

    @Override
    public MitoticManipulation copy() {
        return new MitoticManipulation(this);
    }
}

enum MitoticManipulationPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT,
                        input.getPlayerId(), input.getSource(), game
                )
                .stream()
                .anyMatch(permanent -> permanent.sharesName(input.getObject(), game));
    }
}
