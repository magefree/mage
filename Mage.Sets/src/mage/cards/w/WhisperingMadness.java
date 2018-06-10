
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CipherEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class WhisperingMadness extends CardImpl {

    public WhisperingMadness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{B}");


        // Each player discards their hand, then draws cards equal to the greatest number of cards a player discarded this way.
        this.getSpellAbility().addEffect(new WhisperingMadnessEffect());
        // Cipher
        this.getSpellAbility().addEffect(new CipherEffect());
    }

    public WhisperingMadness(final WhisperingMadness card) {
        super(card);
    }

    @Override
    public WhisperingMadness copy() {
        return new WhisperingMadness(this);
    }
}

class WhisperingMadnessEffect extends OneShotEffect {
    WhisperingMadnessEffect() {
        super(Outcome.Discard);
        staticText = "Each player discards their hand, then draws cards equal to the greatest number of cards a player discarded this way";
    }

    WhisperingMadnessEffect(final WhisperingMadnessEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int maxDiscarded = 0;
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        if (sourcePlayer == null) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(sourcePlayer.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int discarded = 0;
                for (Card c : player.getHand().getCards(game)) {
                    if (player.discard(c, source, game)) {
                        discarded++;
                    }
                }
                if (discarded > maxDiscarded) {
                    maxDiscarded = discarded;
                }
            }
        }
        for (UUID playerId : game.getState().getPlayersInRange(sourcePlayer.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.drawCards(maxDiscarded, game);
            }
        }

        return true;
    }

    @Override
    public WhisperingMadnessEffect copy() {
        return new WhisperingMadnessEffect(this);
    }
}