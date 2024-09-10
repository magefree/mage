package mage.cards.c;

import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class CommonBond extends CardImpl {

    public CommonBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}{W}");

        // Put a +1/+1 counter on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("1st"));

        // Put a +1/+1 counter on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setTargetPointer(new SecondTargetPointer()).concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("2nd"));

    }

    private CommonBond(final CommonBond card) {
        super(card);
    }

    @Override
    public CommonBond copy() {
        return new CommonBond(this);
    }
}
