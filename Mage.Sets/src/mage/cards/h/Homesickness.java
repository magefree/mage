package mage.cards.h;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author muz
 */
public final class Homesickness extends CardImpl {

    public Homesickness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Target player draws two cards. Tap up to two target creatures. Put a stun counter on each of them.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new TapTargetEffect()
            .setText("tap up to two target creatures")
            .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
            .setText("put a stun counter on each of them")
            .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private Homesickness(final Homesickness card) {
        super(card);
    }

    @Override
    public Homesickness copy() {
        return new Homesickness(this);
    }
}
