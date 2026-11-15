package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterAbility;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author Styxo
 */
public class AbilitiesCostReductionControllerEffect extends CostModificationEffectImpl {

    private final FilterAbility filter;
    private final int amount;
    private final boolean excludeSource;

    public AbilitiesCostReductionControllerEffect(FilterAbility filter) {
        this(filter, 1);
    }

    public AbilitiesCostReductionControllerEffect(FilterAbility filter, int amount) {
        this(filter, amount, false);
    }

    public AbilitiesCostReductionControllerEffect(FilterAbility filter, int amount, boolean excludeSource) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = filter;
        this.amount = amount;
        this.excludeSource = excludeSource;
        this.staticText = makeText(filter, amount, excludeSource);
    }

    public AbilitiesCostReductionControllerEffect(AbilitiesCostReductionControllerEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.amount = effect.amount;
        this.excludeSource = effect.excludeSource;
    }

    /**
     * Filter message is the subject of the text:
     * <p>
     * - abilities: "equip abilities you activate" -> "equip abilities you activate cost {1} less to activate"
     * <p>
     * - costs (old template): "Equip costs you pay" -> "Equip costs you pay cost {1} less"
     */
    private static String makeText(FilterAbility filter, int amount, boolean excludeSource) {
        String message = filter.getMessage();
        if (excludeSource && !message.contains("other ")) {
            throw new IllegalArgumentException("Wrong code usage: filter message must contain other objects for excluded source - " + message);
        }
        return message + " cost {" + amount + "} less" + (message.contains("abilities") ? " to activate" : "");
    }

    @Override
    public AbilitiesCostReductionControllerEffect copy() {
        return new AbilitiesCostReductionControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {

        if (excludeSource && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return false;
        }
        
        CardUtil.reduceCost(abilityToModify, amount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.isControlledBy(source.getControllerId())
                && filter.match(abilityToModify, game);
    }
}
