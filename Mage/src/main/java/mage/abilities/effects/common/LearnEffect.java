package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class LearnEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Lesson card");

    static {
        filter.add(SubType.LESSON.getPredicate());
    }

    public LearnEffect() {
        super(Outcome.Neutral);
        staticText = "learn. <i>(You may reveal a Lesson card you own from outside the game " +
                "and put it into your hand, or discard a card to draw a card.)</i>";
    }

    private LearnEffect(final LearnEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return new WishEffect(filter, true).apply(game, source)
                || new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new DiscardCardCost()
        ).apply(game, source);
    }

    @Override
    public LearnEffect copy() {
        return new LearnEffect(this);
    }
}
