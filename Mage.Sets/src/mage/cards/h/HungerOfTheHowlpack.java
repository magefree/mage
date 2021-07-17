
package mage.cards.h;

import java.util.UUID;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class HungerOfTheHowlpack extends CardImpl {

    public HungerOfTheHowlpack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");


        // Put a +1/+1 counter on target creature.
        // <i>Morbid</i> &mdash; Put three +1/+1 counters on that creature instead if a creature died this turn.
        this.getSpellAbility().addEffect(
                new ConditionalOneShotEffect(
                        new AddCountersTargetEffect(CounterType.P1P1.createInstance(3)),
                        new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                        MorbidCondition.instance,
                        "Put a +1/+1 counter on target creature.<br><i>Morbid</i> &mdash; Put three +1/+1 counters on that creature instead if a creature died this turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(MorbidHint.instance);
    }

    private HungerOfTheHowlpack(final HungerOfTheHowlpack card) {
        super(card);
    }

    @Override
    public HungerOfTheHowlpack copy() {
        return new HungerOfTheHowlpack(this);
    }
}
