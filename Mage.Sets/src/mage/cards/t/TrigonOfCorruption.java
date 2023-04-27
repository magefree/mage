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
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nantuko
 */
public final class TrigonOfCorruption extends CardImpl {

    public TrigonOfCorruption (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Trigon of Corruption enters the battlefield with three charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(3)), ""));

        // {2}, {T}, Remove a charge counter from Trigon of Corruption: Put a -1/-1 counter on target creature.
        Costs costs = new CostsImpl();
        costs.add(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        costs.add(new TapSourceCost());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.M1M1.createInstance()), costs);
        ability.addManaCost(new GenericManaCost(2));
        Target target = new TargetCreaturePermanent();
        ability.addTarget(target);
        this.addAbility(ability);

        // {B}{B}, {T}: Put a charge counter on Trigon of Corruption.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new TapSourceCost());
        ability2.addManaCost(new ManaCostsImpl<>("{B}{B}"));
        this.addAbility(ability2);
    }

    public TrigonOfCorruption (final TrigonOfCorruption card) {
        super(card);
    }

    @Override
    public TrigonOfCorruption copy() {
        return new TrigonOfCorruption(this);
    }

}
