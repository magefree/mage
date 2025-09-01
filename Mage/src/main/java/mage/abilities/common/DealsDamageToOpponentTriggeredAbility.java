
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author noxx
 */
public class DealsDamageToOpponentTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean onlyCombat;
    private final boolean setTargetPointer;

    public DealsDamageToOpponentTriggeredAbility(Effect effect) {
        this(effect, false, false, false);
    }

    public DealsDamageToOpponentTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false, false);
    }

    public DealsDamageToOpponentTriggeredAbility(Effect effect, boolean optional, boolean onlyCombat) {
        this(effect, optional, onlyCombat, false);
    }

    public DealsDamageToOpponentTriggeredAbility(Effect effect, boolean optional, boolean onlyCombat, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.onlyCombat = onlyCombat;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever {this} deals " + (onlyCombat ? "combat " : "") + "damage to an opponent, ");
        this.withRuleTextReplacement(true);
    }

    protected DealsDamageToOpponentTriggeredAbility(final DealsDamageToOpponentTriggeredAbility ability) {
        super(ability);
        this.onlyCombat = ability.onlyCombat;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealsDamageToOpponentTriggeredAbility copy() {
        return new DealsDamageToOpponentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.getSourceId())
                || !game.getOpponents(this.getControllerId()).contains(event.getTargetId())
                || onlyCombat
                && !((DamagedPlayerEvent) event).isCombatDamage()) {
            return false;
        }
        this.getEffects().setValue("damage", event.getAmount());
        if (setTargetPointer) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        }
        return true;
    }
}
