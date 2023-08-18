
package mage.cards.h;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author maxlebedev
 */
public final class HeavensGate extends CardImpl {

    public HeavensGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // One or more target creatures become white until end of turn.
        Effect effect = new BecomesColorTargetEffect(ObjectColor.WHITE, Duration.EndOfTurn);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1, Integer.MAX_VALUE));

    }

    private HeavensGate(final HeavensGate card) {
        super(card);
    }

    @Override
    public HeavensGate copy() {
        return new HeavensGate(this);
    }

}
