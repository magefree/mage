
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class EvolutionVat extends CardImpl {

    public EvolutionVat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {3}, {tap}: Tap target creature and put a +1/+1 counter on it. Until end of turn, that creature gains "{2}{G}{U}: Double the number of +1/+1 counters on this creature."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new ManaCostsImpl<>("{3}"));
        ability.addCost(new TapSourceCost());
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("and put a +1/+1 counter on it");
        ability.addEffect(effect);
        effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(), new CountersSourceCount(CounterType.P1P1), false);
        effect.setText("Double the number of +1/+1 counters on this creature");
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}{G}{U}"));
        ability.addEffect(new GainAbilityTargetEffect(gainedAbility, Duration.EndOfTurn,
            "Until end of turn, that creature gains \"{2}{G}{U}: Double the number of +1/+1 counters on this creature.\""));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private EvolutionVat(final EvolutionVat card) {
        super(card);
    }

    @Override
    public EvolutionVat copy() {
        return new EvolutionVat(this);
    }
}
