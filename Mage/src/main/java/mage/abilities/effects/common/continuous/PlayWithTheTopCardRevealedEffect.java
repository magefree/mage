package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author nantuko
 */
public class PlayWithTheTopCardRevealedEffect extends ContinuousEffectImpl {

    protected boolean allPlayers;

    public PlayWithTheTopCardRevealedEffect() {
        this(false);
    }

    public PlayWithTheTopCardRevealedEffect(boolean allPlayers) {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Detriment);
        this.allPlayers = allPlayers;
        if (allPlayers) {
            staticText = "Players play with the top card of their libraries revealed.";
        } else {
            staticText = "Play with the top card of your library revealed";
        }
    }

    protected PlayWithTheTopCardRevealedEffect(final PlayWithTheTopCardRevealedEffect effect) {
        super(effect);
        this.allPlayers = effect.allPlayers;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (allPlayers) {
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null && canLookAtNextTopLibraryCard(game)) {
                        player.setTopCardRevealed(true);
                    }
                }
            } else if (canLookAtNextTopLibraryCard(game)) {
                controller.setTopCardRevealed(true);
            }
            return true;
        }
        return false;
    }

    @Override
    public PlayWithTheTopCardRevealedEffect copy() {
        return new PlayWithTheTopCardRevealedEffect(this);
    }

}
