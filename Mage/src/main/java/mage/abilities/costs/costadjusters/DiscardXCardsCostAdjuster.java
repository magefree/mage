package mage.abilities.costs.costadjusters;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

/**
 * Used to setup discard cost with {X} mana cost
 * <p>
 * If you don't have {X} then use DiscardXTargetCost instead
 * <p>
 * Example:
 * - {X}{1}{B}
 * - As an additional cost to cast this spell, discard X cards.
 *
 * @author JayDi85
 */
public class DiscardXCardsCostAdjuster implements CostAdjuster {

    private final FilterCard filter;

    private DiscardXCardsCostAdjuster(FilterCard filter) {
        this.filter = filter;
    }

    @Override
    public void prepareX(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return;
        }

        int minX = 0;
        int maxX = controller.getHand().getCards(this.filter, ability.getControllerId(), ability, game).size();
        ability.setVariableCostsMinMax(minX, maxX);
    }

    @Override
    public void prepareCost(Ability ability, Game game) {
        int x = CardUtil.getSourceCostsTagX(game, ability, -1);
        if (x >= 0) {
            ability.addCost(new DiscardTargetCost(new TargetCardInHand(x, x, this.filter)));
        }
    }

    public static void addAdjusterAndMessage(Card card, FilterCard filter) {
        addAdjusterAndMessage(card, filter, false);
    }

    public static void addAdjusterAndMessage(Card card, FilterCard filter, boolean isRandom) {
        if (card.getSpellAbility().getManaCosts().getVariableCosts().isEmpty()) {
            // how to fix: use DiscardXTargetCost
            throw new IllegalArgumentException("Wrong code usage: that's cost adjuster must be used with {X} in mana costs only - " + card);
        }

        Ability ability = new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("As an additional cost to cast this spell, discard X " + filter.getMessage())
        );
        ability.setRuleAtTheTop(true);
        card.addAbility(ability);

        card.getSpellAbility().setCostAdjuster(new DiscardXCardsCostAdjuster(filter));
    }
}
