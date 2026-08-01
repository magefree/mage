package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author muz
 */
public final class StunningShot extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public StunningShot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Put two +1/+1 counters on up to one target creature you control. Tap up to one target creature an opponent controls and put a stun counter on it.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 1).setTargetTag(1));
        this.getSpellAbility().addEffect(new TapTargetEffect()
            .setTargetPointer(new SecondTargetPointer())
        );
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
            .setTargetPointer(new SecondTargetPointer())
            .setText("and put a stun counter on it"));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter).setTargetTag(2));
    }

    private StunningShot(final StunningShot card) {
        super(card);
    }

    @Override
    public StunningShot copy() {
        return new StunningShot(this);
    }
}
