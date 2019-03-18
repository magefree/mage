
package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author Styxo
 */
public class SourceCostReductionForEachCardInGraveyardEffect extends CostModificationEffectImpl {

    private FilterCard filter;

    public SourceCostReductionForEachCardInGraveyardEffect() {
        this(new FilterCard());
    }

    public SourceCostReductionForEachCardInGraveyardEffect(FilterCard filter) {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = filter;
        staticText = "{this} costs {1} less to cast for each " + filter.getMessage() + " in your graveyard";
    }

    SourceCostReductionForEachCardInGraveyardEffect(SourceCostReductionForEachCardInGraveyardEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int reductionAmount = player.getGraveyard().count(filter, game);
            CardUtil.reduceCost(abilityToModify, reductionAmount);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility) && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public SourceCostReductionForEachCardInGraveyardEffect copy() {
        return new SourceCostReductionForEachCardInGraveyardEffect(this);
    }
}
