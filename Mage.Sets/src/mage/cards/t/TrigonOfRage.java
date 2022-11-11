

package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class TrigonOfRage extends CardImpl {

    public TrigonOfRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(3)), ""));

        Costs costs = new CostsImpl();
        costs.add(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        costs.add(new TapSourceCost());
        Effect pumpEffect = new BoostTargetEffect(3, 0, Duration.EndOfTurn);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, pumpEffect, costs);
        ability.addManaCost(new GenericManaCost(2));
        Target target = new TargetCreaturePermanent();
        ability.addTarget(target);
        this.addAbility(ability);

        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new TapSourceCost());
        ability2.addManaCost(new ManaCostsImpl<>("{R}{R}"));
        this.addAbility(ability2);
    }

    private TrigonOfRage(final TrigonOfRage card) {
        super(card);
    }

    @Override
    public TrigonOfRage copy() {
        return new TrigonOfRage(this);
    }

}
