package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class CostsLessForExiledCardsEffect extends CostModificationEffectImpl {

    private final FilterCard filter;

    private CostsLessForExiledCardsEffect(FilterCard filter) {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = filter;
    }

    private CostsLessForExiledCardsEffect(final CostsLessForExiledCardsEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        for (Cost cost : spellAbility.getCosts()) {
            if (!(cost instanceof ExileFromHandCost)) {
                continue;
            }
            ExileFromHandCost eCost = (ExileFromHandCost) cost;
            int reduction;
            if (game.inCheckPlayableState()) {
                reduction = game.getPlayer(spellAbility.getControllerId()).getHand().count(filter, game);
            } else {
                reduction = eCost.getCards().size();
            }
            CardUtil.adjustCost(spellAbility, reduction * 2);
            break;
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public CostsLessForExiledCardsEffect copy() {
        return new CostsLessForExiledCardsEffect(this);
    }

    public static void addCostAndEffect(Card card, FilterCard filter) {
        card.getSpellAbility().addCost(new ExileFromHandCost(
                new TargetCardInHand(0, Integer.MAX_VALUE, filter)
        ).setText("as an additional cost to cast this spell, you may exile any number of "
                + filter.getMessage() + ". This spell costs {2} less to cast for each card exiled this way"));
        Ability ability = new SimpleStaticAbility(Zone.ALL, new CostsLessForExiledCardsEffect(filter));
        ability.setRuleVisible(false);
        card.addAbility(ability);
    }
}
