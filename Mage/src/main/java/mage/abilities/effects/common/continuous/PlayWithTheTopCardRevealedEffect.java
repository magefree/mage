
package mage.abilities.effects.common.continuous;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

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

    public PlayWithTheTopCardRevealedEffect(final PlayWithTheTopCardRevealedEffect effect) {
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
                    if (player != null && !isCastFromPlayersLibrary(game, playerId)) {
                        player.setTopCardRevealed(true);
                    }
                }
            } else if (!isCastFromPlayersLibrary(game, controller.getId())) {
                controller.setTopCardRevealed(true);
            }
            return true;
        }
        return false;
    }

    boolean isCastFromPlayersLibrary(Game game, UUID playerId) {
        if (!game.getStack().isEmpty()) {
            StackObject stackObject = game.getStack().getLast();
            return stackObject instanceof Spell
                    && !((Spell) stackObject).isDoneActivatingManaAbilities()
                    && Zone.LIBRARY.equals(((Spell) stackObject).getFromZone());
        }
        return false;
    }

    @Override
    public PlayWithTheTopCardRevealedEffect copy() {
        return new PlayWithTheTopCardRevealedEffect(this);
    }

}
