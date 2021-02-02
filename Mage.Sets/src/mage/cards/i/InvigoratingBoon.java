
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class InvigoratingBoon extends CardImpl {

    public InvigoratingBoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Whenever a player cycles a card, you may put a +1/+1 counter on target creature.
        Ability ability = new CycleAllTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private InvigoratingBoon(final InvigoratingBoon card) {
        super(card);
    }

    @Override
    public InvigoratingBoon copy() {
        return new InvigoratingBoon(this);
    }
}
