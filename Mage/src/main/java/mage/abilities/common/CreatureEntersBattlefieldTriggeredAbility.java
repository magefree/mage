
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class CreatureEntersBattlefieldTriggeredAbility extends TriggeredAbilityImpl {

    private boolean opponentController;
    protected FilterCreaturePermanent filter = new FilterCreaturePermanent();

    /**
     * optional = false<br>
     * opponentController = false
     *
     * @param effect
     */
    public CreatureEntersBattlefieldTriggeredAbility(Effect effect) {
        this(effect, false, false);
    }

    /**
     * opponentController = false
     *
     * @param effect
     * @param optional
     */
    public CreatureEntersBattlefieldTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    /**
     *
     * @param effect
     * @param optional
     * @param opponentController
     */
    public CreatureEntersBattlefieldTriggeredAbility(Effect effect, boolean optional, boolean opponentController) {
        this(Zone.BATTLEFIELD, effect, optional, opponentController);

    }

    /**
     * @param zone
     * @param effect
     * @param optional
     * @param opponentController
     */
    public CreatureEntersBattlefieldTriggeredAbility(Zone zone, Effect effect, boolean optional, boolean opponentController) {
        this(zone, effect, null, optional, opponentController);
    }

    /**
     * @param zone
     * @param effect
     * @param filter filter the triggering creatures
     * @param optional
     * @param opponentController
     */
    public CreatureEntersBattlefieldTriggeredAbility(Zone zone, Effect effect, FilterCreaturePermanent filter, boolean optional, boolean opponentController) {
        super(zone, effect, optional);
        this.opponentController = opponentController;
        if (filter != null) {
            this.filter = filter;
        }
    }

    public CreatureEntersBattlefieldTriggeredAbility(CreatureEntersBattlefieldTriggeredAbility ability) {
        super(ability);
        this.opponentController = ability.opponentController;
        this.filter = ability.filter;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (filter.match(permanent, sourceId, controllerId, game)
                && (permanent.isControlledBy(this.controllerId) ^ opponentController)) {
            if (!this.getTargets().isEmpty()) {
                Target target = this.getTargets().get(0);
                if (target instanceof TargetPlayer) {
                    target.add(permanent.getControllerId(), game);
                }
                if (target instanceof TargetCreaturePermanent) {
                    target.add(event.getTargetId(), game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a " + filter.getMessage() + " enters the battlefield under "
                + (opponentController ? "an opponent's control, " : "your control, ")
                + super.getRule();
    }

    @Override
    public CreatureEntersBattlefieldTriggeredAbility copy() {
        return new CreatureEntersBattlefieldTriggeredAbility(this);
    }
}
