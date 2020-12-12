

package mage.abilities.effects.common.discard;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class DiscardHandTargetEffect extends OneShotEffect {

    protected String targetDescription;
    
    public DiscardHandTargetEffect() {
        this("");
    }
    public DiscardHandTargetEffect(String targetDescription) {
        super(Outcome.Discard);
        this.targetDescription = targetDescription;
    }

    public DiscardHandTargetEffect(final DiscardHandTargetEffect effect) {
        super(effect);
        this.targetDescription = effect.targetDescription;
    }

    @Override
    public DiscardHandTargetEffect copy() {
        return new DiscardHandTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId: getTargetPointer().getTargets(game, source)) {
                Player player = game.getPlayer(playerId);                
                if (player != null) {
                    player.discard(player.getHand().size(), false, false, source, game);
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (!targetDescription.isEmpty()) {
            sb.append(targetDescription);
        } else {
            sb.append("target ").append(mode.getTargets().get(0).getTargetName());
        }
        sb.append(" discards their hand");
        return sb.toString();
    }    
}