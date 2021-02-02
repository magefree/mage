
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class TandemTactics extends CardImpl {

    public TandemTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Up to two target creatures each get +1/+2 until end of turn. You gain 2 life.
        Effect effect = new BoostTargetEffect(1, 2, Duration.EndOfTurn);
        effect.setText("Up to two target creatures each get +1/+2 until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
    }

    private TandemTactics(final TandemTactics card) {
        super(card);
    }

    @Override
    public TandemTactics copy() {
        return new TandemTactics(this);
    }
}
