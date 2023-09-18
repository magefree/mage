
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class DamnablePact extends CardImpl {

    public DamnablePact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{B}{B}");

        // Target player draws X cards and loses X life.
        this.getSpellAbility().addEffect(new DamnablePactEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private DamnablePact(final DamnablePact card) {
        super(card);
    }

    @Override
    public DamnablePact copy() {
        return new DamnablePact(this);
    }
}

class DamnablePactEffect extends OneShotEffect {

    public DamnablePactEffect() {
        super(Outcome.Neutral);
        staticText = "Target player draws X cards and loses X life";
    }

    private DamnablePactEffect(final DamnablePactEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            targetPlayer.drawCards(source.getManaCostsToPay().getX(), source, game);
            targetPlayer.loseLife(source.getManaCostsToPay().getX(), game, source, false);
            return true;
        }
        return false;
    }

    @Override
    public DamnablePactEffect copy() {
        return new DamnablePactEffect(this);
    }
}
