package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CipherEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WhisperingMadness extends CardImpl {

    public WhisperingMadness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}");

        // Each player discards their hand, then draws cards equal to the greatest number of cards a player discarded this way.
        this.getSpellAbility().addEffect(new WhisperingMadnessEffect());

        // Cipher
        this.getSpellAbility().addEffect(new CipherEffect());
    }

    private WhisperingMadness(final WhisperingMadness card) {
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

    private WhisperingMadnessEffect(final WhisperingMadnessEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int maxDiscarded = 0;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int discarded = player.discard(player.getHand(), false, source, game).size();
            if (discarded > maxDiscarded) {
                maxDiscarded = discarded;
            }
        }
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.drawCards(maxDiscarded, source, game);
            }
        }
        return true;
    }

    @Override
    public WhisperingMadnessEffect copy() {
        return new WhisperingMadnessEffect(this);
    }
}
