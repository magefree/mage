package mage.abilities.abilityword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.target.Target;
import mage.util.ManaUtil;

/**
 * @author LevelX2
 */

public class StriveAbility extends SimpleStaticAbility {

    private final String striveCost;

    public StriveAbility(String manaString) {
        super(Zone.STACK, new StriveCostIncreasingEffect(new ManaCostsImpl<>(manaString)));
        setRuleAtTheTop(true);
        this.striveCost = manaString;
        setAbilityWord(AbilityWord.STRIVE);
    }

    public StriveAbility(final StriveAbility ability) {
        super(ability);
        this.striveCost = ability.striveCost;
    }

    @Override
    public SimpleStaticAbility copy() {
        return new StriveAbility(this);
    }

    @Override
    public String getRule() {
        return abilityWord.formatWord() + "This spell costs "
                + striveCost + " more to cast for each target beyond the first.";
    }
}

class StriveCostIncreasingEffect extends CostModificationEffectImpl {

    private ManaCostsImpl striveCosts = null;

    public StriveCostIncreasingEffect(ManaCostsImpl striveCosts) {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.striveCosts = striveCosts;
    }

    protected StriveCostIncreasingEffect(StriveCostIncreasingEffect effect) {
        super(effect);
        this.striveCosts = effect.striveCosts;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        for (Target target : abilityToModify.getTargets()) {
            if (target.getMaxNumberOfTargets() == Integer.MAX_VALUE) { // strive works with "any number of target" only
                int additionalTargets = target.getTargets().size() - 1;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < additionalTargets; i++) {
                    // Build up a string of strive costs for each target
                    sb.append(striveCosts.getText());
                }
                String finalCost = ManaUtil.condenseManaCostString(sb.toString());
                abilityToModify.getManaCostsToPay().add(new ManaCostsImpl<>(finalCost));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getSourceId().equals(source.getSourceId()) && (abilityToModify instanceof SpellAbility);
    }

    @Override
    public StriveCostIncreasingEffect copy() {
        return new StriveCostIncreasingEffect(this);
    }
}
