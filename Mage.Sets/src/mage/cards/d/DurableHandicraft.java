
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class DurableHandicraft extends CardImpl {

    public DurableHandicraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");

        // Whenever a creature enters the battlefield under your control, you may pay {1}. If you do, put a +1/+1 counter on that creature.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("put a +1/+1 counter on that creature");
        DoIfCostPaid doIfCostPaid = new DoIfCostPaid(effect, new GenericManaCost(1));
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, doIfCostPaid, new FilterControlledCreaturePermanent(), false, SetTargetPointer.PERMANENT, null));

        // {5}{G}, Sacrifice Durable Handicraft: Put a +1/+1 counter on each creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent()),
                new ManaCostsImpl("{5}{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private DurableHandicraft(final DurableHandicraft card) {
        super(card);
    }

    @Override
    public DurableHandicraft copy() {
        return new DurableHandicraft(this);
    }
}

