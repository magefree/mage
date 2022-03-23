
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class GnarledEffigy extends CardImpl {

    public GnarledEffigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {4}, {tap}: Put a -1/-1 counter on target creature.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.M1M1.createInstance()),
                new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GnarledEffigy(final GnarledEffigy card) {
        super(card);
    }

    @Override
    public GnarledEffigy copy() {
        return new GnarledEffigy(this);
    }
}
