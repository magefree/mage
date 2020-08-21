package mage.abilities;

import java.util.UUID;
import mage.ApprovingObject;
import mage.constants.AbilityType;
import mage.constants.AsThoughEffectType;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
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
        ApprovingObject approvingObject = game.getContinuousEffects().asThough(getSourceId(), AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, null, playerId, game);
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
