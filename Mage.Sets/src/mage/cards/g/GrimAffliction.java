package mage.cards.g;

import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class GrimAffliction extends CardImpl {

    public GrimAffliction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Put a -1/-1 counter on target creature, then proliferate. (You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.M1M1.createInstance()));
        this.getSpellAbility().addEffect(new ProliferateEffect().concatBy(", then"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GrimAffliction(final GrimAffliction card) {
        super(card);
    }

    @Override
    public GrimAffliction copy() {
        return new GrimAffliction(this);
    }
}
