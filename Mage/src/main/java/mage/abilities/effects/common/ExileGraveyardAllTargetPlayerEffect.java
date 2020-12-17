

package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class ExileGraveyardAllTargetPlayerEffect extends OneShotEffect {

    public ExileGraveyardAllTargetPlayerEffect() {
        super(Outcome.Exile);
        staticText = "exile all cards from target player's graveyard";
    }

    @Override
    public ExileGraveyardAllTargetPlayerEffect copy() {
        return new ExileGraveyardAllTargetPlayerEffect();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetPlayer != null && controller != null) {
            return targetPlayer.moveCards(targetPlayer.getGraveyard(), Zone.EXILED, source, game);
        }
        return false;
    }
}
