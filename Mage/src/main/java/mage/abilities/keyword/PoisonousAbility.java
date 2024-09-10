
package mage.abilities.keyword;

import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;

/**
 * @author dokkaebi
 *
 * 702.69. Poisonous
 * 702.69a Poisonous is a triggered ability. “Poisonous N” means “Whenever this creature deals combat damage to a player, that player gets N poison counters.” (For information about poison counters, see rule 104.3d.)
 * 702.69b If a creature has multiple instances of poisonous, each triggers separately.
 */
public class PoisonousAbility extends DealsCombatDamageToAPlayerTriggeredAbility {

    int n;

    public PoisonousAbility(int n) {
        super(new AddPoisonCountersEffect(n), false, true);
        this.n = n;
    }

    public PoisonousAbility(PoisonousAbility ability) {
        super(ability);
        this.n = ability.n;
    }

    @Override
    public String getRule() {
        return String.format("Poisonous %d. <i>(%s)</i>", n, super.getRule());
    }

    @Override
    public PoisonousAbility copy() {
        return new PoisonousAbility(this);
    }
}

class AddPoisonCountersEffect extends AddCountersTargetEffect {
    public AddPoisonCountersEffect(int n) {
        super(CounterType.POISON.createInstance(n), Outcome.Detriment);
        setText(n == 1 ? "that player gets a poison counter"
                : String.format("that player gets %d poison counters", n));
    }

    private AddPoisonCountersEffect(final AddPoisonCountersEffect effect) {
        super(effect);
    }

    @Override
    public AddPoisonCountersEffect copy() {
        return new AddPoisonCountersEffect(this);
    }
}