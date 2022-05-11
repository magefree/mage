
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public class DealsDamageToAPlayerAllTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private final SetTargetPointer setTargetPointer;
    private final boolean onlyCombat;
    private final boolean affectsDefendingPlayer;
    private final TargetController targetController;

    public DealsDamageToAPlayerAllTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, boolean onlyCombat) {
        this(effect, filter, optional, setTargetPointer, onlyCombat, false);
    }

    public DealsDamageToAPlayerAllTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, boolean onlyCombat, boolean affectsDefendingPlayer) {
        this(Zone.BATTLEFIELD, effect, filter, optional, setTargetPointer, onlyCombat, affectsDefendingPlayer);
    }

    public DealsDamageToAPlayerAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, boolean onlyCombat, boolean affectsDefendingPlayer) {
        this(zone, effect, filter, optional, setTargetPointer, onlyCombat, affectsDefendingPlayer, TargetController.ANY);
    }

    public DealsDamageToAPlayerAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, boolean onlyCombat, boolean affectsDefendingPlayer, TargetController targetController) {
        super(zone, effect, optional);
        this.setTargetPointer = setTargetPointer;
        this.filter = filter;
        this.onlyCombat = onlyCombat;
        this.affectsDefendingPlayer = affectsDefendingPlayer;
        this.targetController = targetController;
    }

    public DealsDamageToAPlayerAllTriggeredAbility(final DealsDamageToAPlayerAllTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.filter = ability.filter;
        this.onlyCombat = ability.onlyCombat;
        this.affectsDefendingPlayer = ability.affectsDefendingPlayer;
        this.targetController = ability.targetController;
    }

    @Override
    public DealsDamageToAPlayerAllTriggeredAbility copy() {
        return new DealsDamageToAPlayerAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (onlyCombat && !((DamagedPlayerEvent) event).isCombatDamage()) {
            return false;
        }
        if (targetController == TargetController.OPPONENT
                && !game.getOpponents(getControllerId()).contains(event.getTargetId())) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (!filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        this.getEffects().setValue("damage", event.getAmount());
        this.getEffects().setValue("sourceId", event.getSourceId());
        if (affectsDefendingPlayer) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
        } else {
            switch (setTargetPointer) {
                case PLAYER:
                    this.getEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
                    break;
                case PERMANENT:
                    this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
                    break;
            }
        }
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever " + filter.getMessage() + " deals " + (onlyCombat ? "combat " : "") + "damage to "
                + (targetController == TargetController.OPPONENT ? "an opponent" : "a player") + ", " ;
    }
}
