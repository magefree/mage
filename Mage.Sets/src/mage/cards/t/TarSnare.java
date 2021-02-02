
package mage.cards.t;

import java.util.UUID;
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
public final class TarSnare extends CardImpl {

    public TarSnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        // Target creature gets -3/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, -2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TarSnare(final TarSnare card) {
        super(card);
    }

    @Override
    public TarSnare copy() {
        return new TarSnare(this);
    }
}
