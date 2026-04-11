package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author muz
 */
public final class CostOfBrilliance extends CardImpl {

    public CostOfBrilliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target player draws two cards and loses 2 life. Put a +1/+1 counter on up to one target creature.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2).setTargetPointer(new FirstTargetPointer()));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(2).withTargetDescription("and").setTargetPointer(new FirstTargetPointer()));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
            .setText("Put a +1/+1 counter on up to one target creature")
            .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
    }

    private CostOfBrilliance(final CostOfBrilliance card) {
        super(card);
    }

    @Override
    public CostOfBrilliance copy() {
        return new CostOfBrilliance(this);
    }
}
