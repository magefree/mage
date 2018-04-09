package mage.abilities.common;

import mage.abilities.condition.common.LegendaryCondition;
import mage.constants.Zone;

/**
 * @author JRHerlehy
 *         Created on 4/8/18.
 */
public class LegendarySpellAbility extends SimpleStaticAbility {

    public LegendarySpellAbility() {
        super(Zone.ALL, new CastOnlyIfConditionIsTrueEffect(LegendaryCondition.instance));
        this.setRuleAtTheTop(true);
        this.getEffects().get(0).setText("<i>(You may cast a legendary sorcery only if you control a legendary creature or planeswalker.)</i>");
    }

    private LegendarySpellAbility(final LegendarySpellAbility ability) {
        super(ability);
    }

    @Override
    public LegendarySpellAbility copy() {
        return new LegendarySpellAbility(this);
    }
}
