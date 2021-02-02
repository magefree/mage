package mage.cards.v;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author North
 */
public final class VoltCharge extends CardImpl {

    public VoltCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Volt Charge deals 3 damage to any target. Proliferate. (You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new ProliferateEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private VoltCharge(final VoltCharge card) {
        super(card);
    }

    @Override
    public VoltCharge copy() {
        return new VoltCharge(this);
    }
}
