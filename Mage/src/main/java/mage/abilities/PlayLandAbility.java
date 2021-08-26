package mage.abilities;

import mage.ApprovingObject;
import mage.constants.AbilityType;
import mage.constants.AsThoughEffectType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PlayLandAbility extends ActivatedAbilityImpl {

    public PlayLandAbility(String cardName) {
        super(AbilityType.PLAY_LAND, Zone.HAND);
        this.usesStack = false;
        this.name = "Play " + cardName;
    }

    public PlayLandAbility(PlayLandAbility ability) {
        super(ability);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // 20210723 - 116.2a
        // Playing a land is a special action. To play a land, a player puts that land onto the battlefield
        // from the zone it was in (usually that player’s hand). By default, a player can take this action
        // only once during each of their turns. A player can take this action any time they have priority
        // and the stack is empty during a main phase of their turn. See rule 305, “Lands.”

        // no super.canActivate() call

        ApprovingObject approvingObject = game.getContinuousEffects().asThough(getSourceId(), AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, this, playerId, game);
        if (!controlsAbility(playerId, game) && null == approvingObject) {
            return ActivationStatus.getFalse();
        }
        //20091005 - 114.2a
        return new ActivationStatus(game.isActivePlayer(playerId)
                && game.getPlayer(playerId).canPlayLand()
                && game.canPlaySorcery(playerId),
                approvingObject);
    }

    @Override
    public String getGameLogMessage(Game game) {
        return " plays " + getMessageText(game);
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getRule() {
        return this.name;
    }

    @Override
    public PlayLandAbility copy() {
        return new PlayLandAbility(this);
    }

}
