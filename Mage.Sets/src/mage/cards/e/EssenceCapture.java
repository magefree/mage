package mage.cards.e;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureSpell;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EssenceCapture extends CardImpl {

    public EssenceCapture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // Counter target creature spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(new FilterCreatureSpell()));

        // Put a +1/+1 counter on up to one target creature you control.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance()
        ).setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 1));
    }

    private EssenceCapture(final EssenceCapture card) {
        super(card);
    }

    @Override
    public EssenceCapture copy() {
        return new EssenceCapture(this);
    }
}
