
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**

 *
 * @author LoneFox
 */
public final class DecreeOfSavagery extends CardImpl {

    public DecreeOfSavagery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{7}{G}{G}");

        // Put four +1/+1 counters on each creature you control.
        this.getSpellAbility().addEffect(new AddCountersAllEffect(CounterType.P1P1.createInstance(4), new FilterControlledCreaturePermanent()));
        // Cycling {4}{G}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{4}{G}{G}")));
        // When you cycle Decree of Savagery, you may put four +1/+1 counters on target creature.
        Ability ability = new CycleTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(4)), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DecreeOfSavagery(final DecreeOfSavagery card) {
        super(card);
    }

    @Override
    public DecreeOfSavagery copy() {
        return new DecreeOfSavagery(this);
    }
}
