package mage.abilities.keyword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public class UndauntedAbility extends SimpleStaticAbility {

    public UndauntedAbility() {
        super(Zone.ALL, new SpellCostReductionForEachSourceEffect(1, OpponentsCount.instance));
        setRuleAtTheTop(true);
    }

    protected UndauntedAbility(final UndauntedAbility ability) {
        super(ability);
    }

    @Override
    public UndauntedAbility copy() {
        return new UndauntedAbility(this);
    }

    @Override
    public String getRule() {
        return "undaunted <i>(This spell costs {1} less to cast for each opponent.)</i>";
    }
}