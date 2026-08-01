package mage.abilities.common;

import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.counters.Counter;

/**
 * @author TheElk801
 */
public class EntersBattlefieldWithCountersAbility extends EntersBattlefieldAbility {

    public EntersBattlefieldWithCountersAbility(Counter counter) {
        this(counter, "it");
    }

    public EntersBattlefieldWithCountersAbility(Counter counter, String noun) {
        super(new AddCountersSourceEffect(counter), "with " + counter.getDescription() + " on " + noun);
    }

    private EntersBattlefieldWithCountersAbility(final EntersBattlefieldWithCountersAbility ability) {
        super(ability);
    }

    @Override
    public EntersBattlefieldWithCountersAbility copy() {
        return new EntersBattlefieldWithCountersAbility(this);
    }
}
