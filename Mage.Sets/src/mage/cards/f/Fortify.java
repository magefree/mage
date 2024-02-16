
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Loki
 */
public final class Fortify extends CardImpl {

    public Fortify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // Choose one - Creatures you control get +2/+0 until end of turn; or creatures you control get +0/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));
        Mode mode = new Mode(new BoostControlledEffect(0, 2, Duration.EndOfTurn));
        this.getSpellAbility().addMode(mode);
    }

    private Fortify(final Fortify card) {
        super(card);
    }

    @Override
    public Fortify copy() {
        return new Fortify(this);
    }
}
