

package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class LevelUpAbility extends ActivatedAbilityImpl {

    public LevelUpAbility(ManaCosts costs) {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.LEVEL.createInstance()), costs);
        this.timing = TimingRule.SORCERY;
    }

    protected LevelUpAbility(final LevelUpAbility ability) {
        super(ability);
    }

    @Override
    public LevelUpAbility copy() {
        return new LevelUpAbility(this);
    }

    @Override
    public String getRule() {
        return new StringBuilder("Level up ").append(getManaCostsToPay().getText())
                .append(" <i>(").append(getManaCostsToPay().getText()).append(": Put a level counter on this. Level up only as a sorcery.)</i>").toString();
    }
}
