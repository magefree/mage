package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

public class DynamicValueCostModidicationEffect extends CostModificationEffectImpl {

    private final UUID originalId;
    private final DynamicValue value;
    private final boolean reducesCost;

    public DynamicValueCostModidicationEffect(Duration duration, Outcome outcome, UUID originalId, DynamicValue value, boolean reducesCost) {
        super(duration, outcome, reducesCost ? CostModificationType.REDUCE_COST : CostModificationType.INCREASE_COST);
        this.originalId = originalId;
        this.value = value;
        this.reducesCost = reducesCost;
        this.staticText = "this ability costs {1} " + (reducesCost ? "less" : "more") + " to activate for each " + value.getMessage();
    }

    private DynamicValueCostModidicationEffect(DynamicValueCostModidicationEffect effect) {
        super(effect);
        this.originalId = effect.originalId;
        this.value = effect.value;
        this.reducesCost = effect.reducesCost;
    }

    @Override
    public DynamicValueCostModidicationEffect copy() {
        return new DynamicValueCostModidicationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int count = value.calculate(game, source, this);
            if (reducesCost) {
                CardUtil.reduceCost(abilityToModify, count);
            } else {
                CardUtil.increaseCost(abilityToModify, count);
            }
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getOriginalId().equals(originalId);
    }

    @Override
    public String getText(Mode mode) {
        return super.getText(mode);
    }
}
