package mage.cards.m;

import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.counters.CounterType;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetadjustment.ConditionalTargetAdjuster;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MindSpiral extends CardImpl {

    public MindSpiral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Gift a tapped Fish
        this.addAbility(new GiftAbility(this, GiftType.TAPPED_FISH));

        // Target player draws three cards. If the gift was promised, tap target creature an opponent controls and put a stun counter on it.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new TapTargetEffect(), GiftWasPromisedCondition.TRUE, "if the gift was promised, " +
                "tap target creature an opponent controls and put a stun counter on it"
        ).addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())).setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(
                GiftWasPromisedCondition.TRUE, true, new TargetOpponentsCreaturePermanent()
        ));
    }

    private MindSpiral(final MindSpiral card) {
        super(card);
    }

    @Override
    public MindSpiral copy() {
        return new MindSpiral(this);
    }
}
