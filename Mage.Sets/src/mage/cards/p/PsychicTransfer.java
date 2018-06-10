
package mage.cards.p;

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
 * @author Quercitron
 */
public final class PsychicTransfer extends CardImpl {

    public PsychicTransfer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}");


        // If the difference between your life total and target player's life total is 5 or less, exchange life totals with that player.
        this.getSpellAbility().addEffect(new PsychicTransferEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public PsychicTransfer(final PsychicTransfer card) {
        super(card);
    }

    @Override
    public PsychicTransfer copy() {
        return new PsychicTransfer(this);
    }
}

class PsychicTransferEffect extends OneShotEffect 
{

    public PsychicTransferEffect() {
        super(Outcome.Neutral);
        this.staticText = "If the difference between your life total and target player's life total is 5 or less, exchange life totals with that player";
    }

    public PsychicTransferEffect(final PsychicTransferEffect effect) {
        super(effect);
    }

    @Override
    public PsychicTransferEffect copy() {
        return new PsychicTransferEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getTargets().getFirstTarget());
        if (sourcePlayer != null && targetPlayer != null) {
            int lifePlayer1 = sourcePlayer.getLife();
            int lifePlayer2 = targetPlayer.getLife();

            if (Math.abs(lifePlayer1 - lifePlayer2) > 5) {
                return false;
            }
            
            if (lifePlayer1 == lifePlayer2) {
                return false;
            }

            // 20110930 - 118.7, 118.8
            if (lifePlayer1 < lifePlayer2 && (!sourcePlayer.isCanGainLife() || !targetPlayer.isCanLoseLife())) {
                return false;
            }

            if (lifePlayer1 > lifePlayer2 && (!sourcePlayer.isCanLoseLife() || !targetPlayer.isCanGainLife())) {
                return false;
            }

            sourcePlayer.setLife(lifePlayer2, game, source);
            targetPlayer.setLife(lifePlayer1, game, source);
            return true;
        }
        return false;
    }
}
