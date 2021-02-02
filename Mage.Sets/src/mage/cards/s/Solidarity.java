
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Plopman
 */
public final class Solidarity extends CardImpl {

    public Solidarity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");


        // Creatures you control get +0/+5 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(0, 5, Duration.EndOfTurn));
    }

    private Solidarity(final Solidarity card) {
        super(card);
    }

    @Override
    public Solidarity copy() {
        return new Solidarity(this);
    }
}
