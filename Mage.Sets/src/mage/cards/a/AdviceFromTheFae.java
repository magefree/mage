package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 *
 * @author awjackson
 */
public final class AdviceFromTheFae extends CardImpl {

    public AdviceFromTheFae(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2/U}{2/U}{2/U}");

        // Look at the top five cards of your library. If you control more creatures than each other player,
        // put two of those cards into your hand. Otherwise, put one of them into your hand.
        // Then put the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new LookLibraryAndPickControllerEffect(5, 2, PutCards.HAND, PutCards.BOTTOM_ANY),
                new LookLibraryAndPickControllerEffect(5, 1, PutCards.HAND, PutCards.BOTTOM_ANY),
                AdviceFromTheFaeCondition.instance, "Look at the top five cards of your library. " +
                "If you control more creatures than each other player, put two of those cards into your hand. " +
                "Otherwise, put one of them into your hand. Then put the rest on the bottom of your library in any order."
        ));
    }

    private AdviceFromTheFae(final AdviceFromTheFae card) {
        super(card);
    }

    @Override
    public AdviceFromTheFae copy() {
        return new AdviceFromTheFae(this);
    }
}

enum AdviceFromTheFaeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        int max = 0;
        UUID controllerId = source.getControllerId();
        for (UUID playerId : game.getState().getPlayersInRange(controllerId, game)) {
            if (!playerId.equals(controllerId)) {
                max = Math.max(max, game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, playerId, game));
            }
        }
        return game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, controllerId, game) > max;
    }

    @Override
    public String toString() {
        return "you control more creatures than each other player";
    }
}
