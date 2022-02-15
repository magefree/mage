package mage.abilities.costs.costadjusters;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class ExileCardsFromHandAdjuster implements CostAdjuster {

    private final FilterCard filter;

    private ExileCardsFromHandAdjuster(FilterCard filter) {
        this.filter = filter;
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (game.inCheckPlayableState()) {
            return;
        }
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return;
        }
        int cardCount = player.getHand().count(filter, game);
        int toExile = cardCount > 0 ? player.getAmount(
                0, cardCount, "Choose how many " + filter.getMessage() + " to exile", game
        ) : 0;
        if (toExile > 0) {
            ability.addCost(new ExileFromHandCost(new TargetCardInHand(toExile, filter)));
            CardUtil.reduceCost(ability, 2 * toExile);
        }
    }

    public static final void addAdjusterAndMessage(Card card, FilterCard filter) {
        card.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new InfoEffect("as an additional cost to cast this spell, you may exile any number of "
                        + filter.getMessage() + ". This spell costs {2} less to cast for each card exiled this way")
        ));
        card.getSpellAbility().setCostAdjuster(new ExileCardsFromHandAdjuster(filter));
    }
}
