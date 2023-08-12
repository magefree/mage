
package mage.abilities.effects.common.continuous;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LoneFox
 */
public class PlayWithHandRevealedEffect extends ContinuousEffectImpl {

    private TargetController who;

    public PlayWithHandRevealedEffect(TargetController who) {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Detriment);
        this.who = who;
    }

    protected PlayWithHandRevealedEffect(final PlayWithHandRevealedEffect effect) {
        super(effect);
        who = effect.who;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Iterable<UUID> affectedPlayers;
            switch (who) {
                case ANY:
                    affectedPlayers = game.getState().getPlayersInRange(controller.getId(), game);
                    break;
                case OPPONENT:
                    affectedPlayers = game.getOpponents(source.getControllerId());
                    break;
                case YOU:
                    List<UUID> tmp = new ArrayList<>();
                    tmp.add(source.getControllerId());
                    affectedPlayers = tmp;
                    break;
                default:
                    return false;
            }

            for (UUID playerID : affectedPlayers) {
                Player player = game.getPlayer(playerID);
                if (player != null) {
                    player.revealCards(player.getName() + "'s hand cards", player.getHand(), game, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public PlayWithHandRevealedEffect copy() {
        return new PlayWithHandRevealedEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        switch (who) {
            case ANY:
                return "Players play with their hands revealed";
            case OPPONENT:
                return "Your opponents play with their hands revealed";
            case YOU:
                return "Play with your hand revealed";
            default:
                return "Unknown TargetController for PlayWithHandRevealedEffect";
        }
    }
}
