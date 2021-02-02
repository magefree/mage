
package mage.cards.o;

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
 * @author LevelX2
 */
public final class OrnamentalCourage extends CardImpl {

    public OrnamentalCourage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Untap target creature. It gets +1/+3 until end of turn.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        Effect effect = new BoostTargetEffect(1, 3, Duration.EndOfTurn);
        effect.setText("It gets +1/+3 until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private OrnamentalCourage(final OrnamentalCourage card) {
        super(card);
    }

    @Override
    public OrnamentalCourage copy() {
        return new OrnamentalCourage(this);
    }
}
