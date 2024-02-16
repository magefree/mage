
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class PowerConduit extends CardImpl {

    public PowerConduit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {tap}, Remove a counter from a permanent you control: Choose one - Put a charge counter on target artifact; or put a +1/+1 counter on target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.CHARGE.createInstance()), new TapSourceCost());
        ability.addCost(new RemoveCounterCost(new TargetControlledPermanent(1, 1, new FilterControlledPermanent(), true)));
        ability.addTarget(new TargetArtifactPermanent());
        Mode mode = new Mode(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        mode.addTarget(new TargetCreaturePermanent());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private PowerConduit(final PowerConduit card) {
        super(card);
    }

    @Override
    public PowerConduit copy() {
        return new PowerConduit(this);
    }
}
