
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class HopeAndGlory extends CardImpl {

    public HopeAndGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Untap two target creatures. Each of them gets +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1, Duration.EndOfTurn)
                .setText("Each of them gets +1/+1 until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));
    }

    private HopeAndGlory(final HopeAndGlory card) {
        super(card);
    }

    @Override
    public HopeAndGlory copy() {
        return new HopeAndGlory(this);
    }
}
