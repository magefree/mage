package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class PsychicTransfer extends CardImpl {

    public PsychicTransfer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // If the difference between your life total and target player's life total is 5 or less, exchange life totals with that player.
        this.getSpellAbility().addEffect(new PsychicTransferEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private PsychicTransfer(final PsychicTransfer card) {
        super(card);
    }

    @Override
    public PsychicTransfer copy() {
        return new PsychicTransfer(this);
    }
}

class PsychicTransferEffect extends OneShotEffect {

    PsychicTransferEffect() {
        super(Outcome.Neutral);
        this.staticText = "If the difference between your life total and target player's " +
                "life total is 5 or less, exchange life totals with that player";
    }

    private PsychicTransferEffect(final PsychicTransferEffect effect) {
        super(effect);
    }

    @Override
    public PsychicTransferEffect copy() {
        return new PsychicTransferEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (sourcePlayer == null || targetPlayer == null
                || Math.abs(sourcePlayer.getLife() - targetPlayer.getLife()) > 5) {
            return false;
        }
        sourcePlayer.exchangeLife(targetPlayer, source, game);
        return true;
    }
}
