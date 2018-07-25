
package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.util.CardUtil;

/**
 * This permanent enters the battlefield with X psi counter(s) on it.
 *
 * @author NinthWorld
 */

public class PsionicAbility extends StaticAbility {

    private int amount;

    public PsionicAbility(int amount) {
        super(Zone.ALL, new EntersBattlefieldEffect(new AddCountersSourceEffect(CounterType.PSI.createInstance(amount))));
        this.amount = amount;
    }

    public PsionicAbility(final PsionicAbility ability) {
        super(ability);
        this.amount = ability.amount;
    }

    @Override
    public PsionicAbility copy() {
        return new PsionicAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Psionic");
        sb.append(' ').append(amount).append(" <i>(This permanent enters the battlefield with ")
                .append(amount == 1 ? "a" : CardUtil.numberToText(amount))
                .append(" psi counter").append(amount > 1 ? "s" : "").append(" on it.");
        return sb.toString();
    }
}
