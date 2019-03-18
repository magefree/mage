

package mage.abilities.effects.common;

import java.util.ArrayList;
import java.util.UUID;
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
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            ArrayList<UUID> graveyard = new ArrayList<>(targetPlayer.getGraveyard());
            for (UUID cardId : graveyard) {
                game.getCard(cardId).moveToZone(Zone.EXILED, cardId, game, false);
            }
            return true;
        }
        return false;
    }
}
