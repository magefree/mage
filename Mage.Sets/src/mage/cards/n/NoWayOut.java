package mage.cards.n;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ZombieDecayedToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NoWayOut extends CardImpl {

    public NoWayOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent discards two cards. You create a 2/2 black Zombie creature token with decayed.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieDecayedToken()).concatBy("You"));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private NoWayOut(final NoWayOut card) {
        super(card);
    }

    @Override
    public NoWayOut copy() {
        return new NoWayOut(this);
    }
}
// how does kevin costner keep getting work?
