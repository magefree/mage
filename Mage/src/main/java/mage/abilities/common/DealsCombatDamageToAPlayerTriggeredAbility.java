package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DealsCombatDamageToAPlayerTriggeredAbility extends TriggeredAbilityImpl {

    protected final boolean setTargetPointer;

    public DealsCombatDamageToAPlayerTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public DealsCombatDamageToAPlayerTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever {this} deals combat damage to a player, ");
    }

    protected DealsCombatDamageToAPlayerTriggeredAbility(final DealsCombatDamageToAPlayerTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealsCombatDamageToAPlayerTriggeredAbility copy() {
        return new DealsCombatDamageToAPlayerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(getSourceId())
                || !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        getAllEffects().setValue("damage", event.getAmount());
        if (setTargetPointer) {
            getAllEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
        }
        return true;
    }

}
