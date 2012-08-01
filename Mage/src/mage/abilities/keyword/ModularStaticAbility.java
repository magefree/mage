package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.StaticAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.counters.CounterType;

/**
 * Modular keyword static part
 * @author Loki
 */
public class ModularStaticAbility extends StaticAbility<ModularStaticAbility> {
    private int amount;

    public ModularStaticAbility(int amount) {
        super(Constants.Zone.BATTLEFIELD, new EntersBattlefieldEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(amount))));
        this.amount = amount;
    }

    public ModularStaticAbility(final ModularStaticAbility ability) {
        super(ability);
        this.amount = ability.amount;
    }

    @Override
    public ModularStaticAbility copy() {
        return new ModularStaticAbility(this);
    }
}
