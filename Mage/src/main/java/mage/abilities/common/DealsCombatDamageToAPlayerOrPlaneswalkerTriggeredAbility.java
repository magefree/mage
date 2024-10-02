package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author xenohedron
 */
public class DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility extends TriggeredAbilityImpl {

    protected final boolean setTargetPointer;

    public DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever {this} deals combat damage to a player or planeswalker, ");
        this.setTargetPointer = setTargetPointer;
    }


    protected DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(final DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility copy() {
        return new DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(getSourceId())
                || !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        if (event.getType() == GameEvent.EventType.DAMAGED_PERMANENT) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent == null || !permanent.isPlaneswalker(game)) {
                return false;
            }
        }
        getAllEffects().setValue("damage", event.getAmount());
        if (setTargetPointer) {
            getAllEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
        }
        return true;
    }

}
