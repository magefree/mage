package mage.abilities.triggers;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author xenohedron
 */
public abstract class AtStepTriggeredAbility extends TriggeredAbilityImpl {

    protected final TargetController targetController;
    private Boolean setTargetPointer; // null for default behavior (set target pointer if no targets)

    protected AtStepTriggeredAbility(Zone zone, TargetController targetController, Effect effect, boolean optional) {
        super(zone, effect, optional);
        this.targetController = targetController;
        setTriggerPhrase(generateTriggerPhrase());
    }

    protected AtStepTriggeredAbility(final AtStepTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (checkTargetController(event, game)) {
            setTargetPointerIfNeeded(event.getPlayerId());
            return true;
        }
        return false;
    }

    private boolean checkTargetController(GameEvent event, Game game) {
        switch (targetController) {
            case YOU:
                return isControlledBy(event.getPlayerId());
            case NOT_YOU:
                return !isControlledBy(event.getPlayerId());
            case ANY:
            case NEXT:
            case EACH_PLAYER:
                return true;
            case OPPONENT:
                Player player = game.getPlayer(getControllerId());
                return player != null && player.hasOpponent(event.getPlayerId(), game);
            case ENCHANTED:
                Permanent permanent = getSourcePermanentIfItStillExists(game);
                return permanent != null && game.isActivePlayer(permanent.getAttachedTo());
            case CONTROLLER_ATTACHED_TO:
                Permanent attachment = getSourcePermanentIfItStillExists(game);
                if (attachment == null) {
                    return false;
                }
                Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
                return attachedTo != null && game.isActivePlayer(attachedTo.getControllerId());
            case MONARCH:
                return event.getPlayerId().equals(game.getMonarchId());
            default:
                throw new UnsupportedOperationException("Unsupported TargetController in AtStepTriggeredAbility: " + targetController);
        }
    }

    /**
     * In rare cases, if the default behavior (of setting the target pointer to the active player id
     * if and only if there are no targets) is not adequate, manually specify whether target pointer should be set
     */
    public AtStepTriggeredAbility withTargetPointerSet(boolean setTargetPointer) {
        this.setTargetPointer = setTargetPointer;
        return this;
    }

    private void setTargetPointerIfNeeded(UUID playerId) {
        if (Boolean.TRUE.equals(setTargetPointer) || (setTargetPointer == null && getTargets().isEmpty())) {
            this.getEffects().setTargetPointer(new FixedTarget(playerId));
        }
    }

    protected abstract String generateTriggerPhrase();

}
