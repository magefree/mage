
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class Hydrosurge extends CardImpl {

    public Hydrosurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Target creature gets -5/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-5, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Hydrosurge(final Hydrosurge card) {
        super(card);
    }

    @Override
    public Hydrosurge copy() {
        return new Hydrosurge(this);
    }
}
