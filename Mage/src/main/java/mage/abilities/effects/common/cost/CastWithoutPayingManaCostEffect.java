package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author fireshoes - Original Code
 * @author JRHerlehy - Implement as seperate class
 * <p>
 * Allows player to choose to cast as card from hand without paying its mana
 * cost.
 * </p>
 */
public class CastWithoutPayingManaCostEffect extends OneShotEffect {

    private final DynamicValue manaCost;

    /**
     * @param maxCost Maximum converted mana cost for this effect to apply to
     */
    public CastWithoutPayingManaCostEffect(int maxCost) {
        this(StaticValue.get(maxCost));
    }

    public CastWithoutPayingManaCostEffect(DynamicValue maxCost) {
        super(Outcome.PlayForFree);
        this.manaCost = maxCost;
        this.staticText = "you may cast a spell with mana value "
                + maxCost + " or less from your hand without paying its mana cost";
    }

    public CastWithoutPayingManaCostEffect(final CastWithoutPayingManaCostEffect effect) {
        super(effect);
        this.manaCost = effect.manaCost;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int cmc = manaCost.calculate(game, source, this);
        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, cmc + 1));
        return CardUtil.castSpellWithAttributesForFree(controller, source, game, controller.getHand(), filter);
    }

    @Override
    public CastWithoutPayingManaCostEffect copy() {
        return new CastWithoutPayingManaCostEffect(this);
    }
}
