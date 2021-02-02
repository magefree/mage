
package mage.cards.i;

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
 * @author LoneFox
 */
public final class Inspirit extends CardImpl {

    public Inspirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Untap target creature. It gets +2/+4 until end of turn.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        Effect effect = new BoostTargetEffect(2, 4, Duration.EndOfTurn);
        effect.setText("It gets +2/+4 until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Inspirit(final Inspirit card) {
        super(card);
    }

    @Override
    public Inspirit copy() {
        return new Inspirit(this);
    }
}
