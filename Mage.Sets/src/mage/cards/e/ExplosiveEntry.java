package mage.cards.e;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExplosiveEntry extends CardImpl {

    public ExplosiveEntry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Destroy up to one target artifact. Put a +1/+1 counter on up to one target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("destroy up to one target artifact")
                .setTargetPointer(new FirstTargetPointer()));
        this.getSpellAbility().addTarget(new TargetArtifactPermanent(0, 1));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("Put a +1/+1 counter on up to one target creature")
                .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
    }

    private ExplosiveEntry(final ExplosiveEntry card) {
        super(card);
    }

    @Override
    public ExplosiveEntry copy() {
        return new ExplosiveEntry(this);
    }
}
