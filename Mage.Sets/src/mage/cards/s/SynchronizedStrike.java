
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author stravant
 */
public final class SynchronizedStrike extends CardImpl {

    public SynchronizedStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Untap up to two target creatures. They each get +2/+2 until end of turn.
        getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        getSpellAbility().addEffect(new UntapTargetEffect());
        Effect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        effect.setText("They each get +2/+2 until end of turn");
        getSpellAbility().addEffect(effect);
    }

    private SynchronizedStrike(final SynchronizedStrike card) {
        super(card);
    }

    @Override
    public SynchronizedStrike copy() {
        return new SynchronizedStrike(this);
    }
}
