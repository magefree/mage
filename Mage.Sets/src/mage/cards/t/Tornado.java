
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.TargetPermanent;

/**
 *
 * @author Quercitron
 */
public final class Tornado extends CardImpl {

    public Tornado(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{G}");


        // Cumulative upkeep {G}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{G}")));
        // {2}{G}, Pay 3 life for each velocity counter on Tornado: Destroy target permanent and put a velocity counter on Tornado. Activate this ability only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{2}{G}"));
        DynamicValue lifeToPayAmount = new MultipliedValue(new CountersSourceCount(CounterType.VELOCITY), 3);
        ability.addCost(new PayLifeCost(lifeToPayAmount, "3 life for each velocity counter on {this}"));
        ability.addTarget(new TargetPermanent());
        Effect effect = new AddCountersSourceEffect(CounterType.VELOCITY.createInstance());
        effect.setText("and put a velocity counter on {this}");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private Tornado(final Tornado card) {
        super(card);
    }

    @Override
    public Tornado copy() {
        return new Tornado(this);
    }
}
