
package mage.cards.r;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.RatCantBlockToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RatOut extends CardImpl {

    public RatOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Up to one target creature gets -1/-1 until end of turn. You create a 1/1 black Rat creature token with "This creature can't block."
        this.getSpellAbility().addEffect(new BoostTargetEffect(-1, -1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new RatCantBlockToken()).concatBy("You"));
    }

    private RatOut(final RatOut card) {
        super(card);
    }

    @Override
    public RatOut copy() {
        return new RatOut(this);
    }
}
